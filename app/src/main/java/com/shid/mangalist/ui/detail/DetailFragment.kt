package com.shid.mangalist.ui.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.databinding.DetailFragmentBinding
import com.shid.mangalist.utils.custom.ExpandableLayout
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var rootView: ScrollView
    lateinit var trans_imageView: ImageView
    val detailViewModel: DetailViewModel by viewModels()
    private lateinit var rv_characters: RecyclerView
    private lateinit var rv_video: RecyclerView
    private lateinit var linearLayout: LinearLayout
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var animeTitle:AppCompatTextView
    private lateinit var animeSummary:AppCompatTextView
    private var anime_id: Int? = null
    private lateinit var backgroundImg:ImageView
    private var _binding : DetailFragmentBinding ?= null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = DetailFragment()
        const val TAG = "LibraryFragment"
        const val posterKey = "posterKey"
        const val paramsKey = "paramsKey"
    }

    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = arguments?.getParcelable<TransformationLayout.Params>(paramsKey)
        onTransformationEndContainer(params)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        }
        callback.isEnabled

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        val view: View = binding.root
        rootView = view.findViewById(R.id.root_detail)
        setUi(view)
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE
        return view
    }

    private fun setUi(view: View) {
        trans_imageView = view.findViewById(R.id.poster_image)
        backgroundImg = view.findViewById(R.id.transformation_image)
        rv_characters = view.findViewById(R.id.cast_list)
        rv_video = view.findViewById(R.id.video_list)
        animeTitle = view.findViewById(R.id.anime_title)
        animeSummary = view.findViewById(R.id.txt_runtime)
        linearLayout = view.findViewById(R.id.list_genres)
        binding.expandButton.setOnClickListener(View.OnClickListener {
            handleExpandAction(view)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val poster = arguments?.getParcelable<AiringAnime>(posterKey)
        poster?.let {

            rootView.transitionName = it.title
            trans_imageView.load(it.imageUrl)
            anime_id = poster.id
            Glide.with(backgroundImg.context).load(it.imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(backgroundImg)

            animeTitle.text = poster.title

        }
        videoAdapter = VideoAdapter { url -> showVideo(url) }
        characterAdapter = CharacterAdapter()





        anime_id?.let { detailViewModel.setDetailAnime(it) }
        detailViewModel.anime.observe(viewLifecycleOwner, Observer {

            animeSummary.text = it.synopsis
            it.genres.let {
                for (genre in it.indices) {
                    val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 16, 0)
                    val genreTextView = TextView(requireContext()).apply {
                        setBackgroundResource(R.drawable.bg_genres)
                        layoutParams = params
                        setTextColor(Color.parseColor("#000000"))
                        text = it[genre].name.toString()
                    }
                    linearLayout.addView(genreTextView)
                }
            }
        })

        detailViewModel.characters.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                rv_characters.apply {
                    setHasFixedSize(true)
                    adapter = characterAdapter
                }
                characterAdapter.setData(it)
            }
        })

        detailViewModel.videos.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                rv_video.apply {
                    setHasFixedSize(true)
                    adapter = videoAdapter
                }
                videoAdapter.setData(it)
            }
        })


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun showVideo(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        try {
            startActivity(intent)
        } catch (t: Throwable) {
            Toast.makeText(context, "Ups, slowly!", Toast.LENGTH_LONG).show()
        }
    }

    fun handleExpandAction(view: View) {

        if (binding.includedLayout.expandableLayout.isExpanded()) {
            binding.expandButton.text = getString(R.string.read_more)
            binding.includedLayout.expandableLayout.collapse()
        } else {
            binding.expandButton.text = getString(R.string.read_less)
            binding.includedLayout.expandableLayout.expand()
        }
    }

}