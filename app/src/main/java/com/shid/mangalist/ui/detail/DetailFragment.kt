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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.irozon.sneaker.Sneaker
import com.like.LikeButton
import com.like.OnLikeListener
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.detail.CharactersListResponse
import com.shid.mangalist.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var videoAdapter: VideoAdapter

    private var anime_id: Int? = null

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = DetailFragment()
        const val TAG = "LibraryFragment"

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val view: View = binding.root
        setUi(view)
        return view
    }

    private fun setUi(view: View) {
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE

        val view = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view.fitsSystemWindows = false
        view.setPadding(0, 0, 0, 0)

        binding.expandButton.setOnClickListener(View.OnClickListener {
            handleExpandAction(view)
        })
        binding.starButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                detailViewModel.anime.value?.let { detailViewModel.setFavorite(it) }
                Sneaker.with(requireActivity()) // Activity, Fragment or ViewGroup
                    .setTitle("Success!!")
                    .setMessage("Anime added to Bookmarks!")
                    .sneakSuccess()
            }

            override fun unLiked(likeButton: LikeButton?) {
                detailViewModel.anime.value?.let { detailViewModel.unSetFavorite(it) }
                Sneaker.with(requireActivity()) // Activity, Fragment or ViewGroup
                    .setTitle("Success!!")
                    .setMessage("Anime removed from Bookmarks!")
                    .sneakSuccess()
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animeId = DetailFragmentArgs.fromBundle(requireArguments()).anime
        detailViewModel.checkIfAnimeIsFavorite(animeId)
        detailViewModel.setDetailAnime(animeId)

        checkIfAnimeIsInDb()
        setAdapters()
        fetchAnimeDetail()
        fetchCast()
        fetchVideos()

    }

    private fun setAdapters() {
        videoAdapter = VideoAdapter { url -> showVideo(url) }
        characterAdapter = CharacterAdapter()
    }

    private fun checkIfAnimeIsInDb() {
        detailViewModel.isAnimeInDb.observe(viewLifecycleOwner, Observer {
            if (it == 1) {
                binding.starButton.isLiked = true
            }
        })
    }

    private fun fetchAnimeDetail() {
        anime_id?.let { detailViewModel.setDetailAnime(it) }
        detailViewModel.anime.observe(viewLifecycleOwner, Observer { it ->

            binding.posterImage.load(it.imageUrl)
            anime_id = it.id
            Glide.with(binding.transformationImage.context).load(it.imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(binding.transformationImage)

            if (it.score.toString() == "null") {
                _binding?.txtScore?.text = "N/A"
            } else {
                _binding?.txtScore?.text = it.score.toString()
            }

            _binding?.rank?.text = it.popularity.toString()
            binding.animeTitle.text = it.title
            binding.txtRuntime.text = it.synopsis
            if (it.episodes.toString() == "null") {
                binding.includedLayout.infoAnimeEpisodes.text = "N/A"
            } else {
                binding.includedLayout.infoAnimeEpisodes.text = it.episodes.toString()
            }

            binding.includedLayout.infoAnimeMembers.text = it.members.toString()
            binding.includedLayout.infoAnimeTitle.text = it.title
            binding.includedLayout.infoAnimePremier.text = it.premiered
            binding.includedLayout.infoAnimeStatus.text = it.status
            binding.includedLayout.infoAnimePopularity.text = it.popularity.toString()
            binding.includedLayout.infoAnimeType.text = it.type

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
                        setTextColor(Color.parseColor("#FFFFFF"))
                        text = it[genre].name.toString()
                    }
                    binding.listGenres.addView(genreTextView)

                }
            }
        })

    }

    private fun fetchCast() {
        detailViewModel.characters.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                updateCharacterDetails(it)
            }
        })
    }

    private fun fetchVideos() {
        detailViewModel.videos.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.videoList.apply {
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
        val characterAdapter2 = CharacterAdapter()
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

    private fun handleExpandAction(view: View) {

        if (binding.includedLayout.expandableLayout.isExpanded) {
            binding.expandButton.text = getString(R.string.read_more)
            binding.includedLayout.expandableLayout.collapse()
        } else {
            binding.expandButton.text = getString(R.string.read_less)
            binding.includedLayout.expandableLayout.expand()
        }
    }

}