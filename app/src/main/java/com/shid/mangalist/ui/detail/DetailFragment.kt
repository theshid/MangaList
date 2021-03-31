package com.shid.mangalist.ui.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.databinding.DetailFragmentBinding
import com.shid.mangalist.ui.more.MoreFragmentArgs
import com.shid.mangalist.ui.more.MoreFragmentDirections
import com.shid.mangalist.utils.GsonParser
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var rootView: ScrollView
    lateinit var trans_imageView: ImageView
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var rv_characters: RecyclerView
    private lateinit var rv_video: RecyclerView
    private lateinit var linearLayout: LinearLayout
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var animeTitle: AppCompatTextView
    private lateinit var animeSummary: AppCompatTextView
    private var anime_id: Int? = null
    private var image_url: String? = null
    private lateinit var backgroundImg: ImageView
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = DetailFragment()
        const val TAG = "LibraryFragment"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //onTransformationEndContainer(params)

        /*val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            goToHome()
        }
        callback.isEnabled*/


    }

    private fun goToHome() {
        findNavController().navigate(
            DetailFragmentDirections.actionDetailFragmentToHomeFragment()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        val view: View = binding.root
        rootView = view.findViewById(R.id.root_detail)

        //val jsonFromActivity = DetailFragmentArgs.fromBundle(requireArguments()).anime
        /*val animeObject =
            GsonParser.getGsonParser()?.fromJson<AiringAnime>(jsonFromActivity,AiringAnime::class.java)*/
        setUi(view)


        //trans_imageView.load(image_url)

        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE

        /*(activity as MainActivity).actionBar?.setDisplayShowHomeEnabled(true)*/
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
        // val poster = arguments?.getParcelable<AiringAnime>(posterKey)

        val animeId = DetailFragmentArgs.fromBundle(requireArguments()).anime
        detailViewModel.setDetailAnime(animeId)
        videoAdapter = VideoAdapter { url -> showVideo(url) }
        characterAdapter = CharacterAdapter()
        anime_id?.let { detailViewModel.setDetailAnime(it) }
        detailViewModel.anime.observe(viewLifecycleOwner, Observer {

            trans_imageView.load(it.imageUrl)
            anime_id = it.id
            Glide.with(backgroundImg.context).load(it.imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(backgroundImg)

            animeTitle.text = it.title
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
                updateCharacterDetails(it)
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

    private fun updateCharacterDetails(list: List<CharactersListResponse>?) {
        binding.includedLayout.castList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.includedLayout.castList.visibility = View.VISIBLE
        val characterAdapter2: CharacterAdapter = CharacterAdapter()
        Log.d("Detail", "size of list:" + (list?.size ?: 0))

        binding.includedLayout.castList.adapter = characterAdapter2
        characterAdapter2.setData(list)
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