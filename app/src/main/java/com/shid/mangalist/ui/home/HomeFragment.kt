package com.shid.mangalist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.shid.mangalist.R
import com.shid.mangalist.utils.enum.More
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var topAiringAdapter: TopAiringAdapter
    private lateinit var topUpcomingAdapter: TopUpcomingAdapter
    private lateinit var topTvAdapter: TopTvAdapter
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var topOvaAdapter: TopOvaAdapter

    private lateinit var airingRecyclerView: RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var tvRecyclerView: RecyclerView
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var ovaRecyclerView: RecyclerView

    private lateinit var txt_moreAiring: TextView
    private lateinit var txt_moreUpcoming: TextView
    private lateinit var txt_moreTv: TextView
    private lateinit var txt_moreMovie: TextView
    private lateinit var txt_moreOva: TextView



    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        configureViews(root)
        fetchTopAnimes()
        clickListeners()

        return root
    }

    companion object{
         lateinit var typeAnime:String
    }

    private fun clickListeners() {
        txt_moreAiring.setOnClickListener(View.OnClickListener {
            showMore(More.AIRING)
        })

        txt_moreUpcoming.setOnClickListener(View.OnClickListener {
            showMore(More.UPCOMING)
        })

        txt_moreTv.setOnClickListener(View.OnClickListener {
            showMore(More.TV)
        })

        txt_moreMovie.setOnClickListener(View.OnClickListener {
            showMore(More.MOVIE)
        })

        txt_moreOva.setOnClickListener(View.OnClickListener {
            showMore(More.OVA)
        })
    }

    private fun showMore(type: More) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoreFragment(type))
        typeAnime = type.type
    }

    private fun configureViews(view: View) {
        txt_moreAiring = view.findViewById(R.id.more_airing)
        txt_moreUpcoming = view.findViewById(R.id.more_upcoming)
        txt_moreTv = view.findViewById(R.id.more_tv)
        txt_moreMovie = view.findViewById(R.id.more_movie)
        txt_moreOva = view.findViewById(R.id.more_ova)


        airingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_airing)
        topAiringAdapter = TopAiringAdapter()
        airingRecyclerView.adapter = topAiringAdapter

        upcomingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_upcoming)
        topUpcomingAdapter = TopUpcomingAdapter()
        upcomingRecyclerView.adapter = topUpcomingAdapter

        tvRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_tv)
        topTvAdapter = TopTvAdapter()
        tvRecyclerView.adapter = topTvAdapter

        movieRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_movie)
        topMovieAdapter = TopMovieAdapter()
        movieRecyclerView.adapter = topMovieAdapter

        ovaRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_ova)
        topOvaAdapter = TopOvaAdapter()
        ovaRecyclerView.adapter = topOvaAdapter
    }

    @ExperimentalPagingApi
    private fun fetchTopAnimes() {
        lifecycleScope.launch {
            homeViewModel.getTopAiringAnimes().collectLatest {
                topAiringAdapter.submitData(it)
            }


        }
        lifecycleScope.launch {
            homeViewModel.getTopUpcomingAnimes().collectLatest {
                topUpcomingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            homeViewModel.getTopMovieAnimes().collectLatest {
                topMovieAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            homeViewModel.getTopTvAnimes().collectLatest {
                topTvAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            homeViewModel.getTopOvaAnimes().collectLatest {
                topOvaAdapter.submitData(it)
            }
        }


    }
}