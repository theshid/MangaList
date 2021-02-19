package com.shid.mangalist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.shid.mangalist.R
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

    private lateinit var airingRecyclerView:RecyclerView
    private lateinit var upcomingRecyclerView:RecyclerView
    private lateinit var tvRecyclerView:RecyclerView
    private lateinit var movieRecyclerView:RecyclerView

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        configureList(root)
        fetchTopAnimes()

        return root
    }

    private fun configureList(view: View) {
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


    }
}