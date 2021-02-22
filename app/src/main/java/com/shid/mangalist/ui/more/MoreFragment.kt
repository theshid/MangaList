package com.shid.mangalist.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.ui.home.*
import com.shid.mangalist.utils.custom.BaseFragment
import com.shid.mangalist.utils.custom.RecyclerItemClickListener
import com.shid.mangalist.utils.custom.RecyclerSnapItemListener
import com.shid.mangalist.utils.custom.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class MoreFragment : BaseFragment(), RecyclerItemClickListener.OnRecyclerViewItemClickListener {
    private val moreViewModel: MoreViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var topAiringAdapter: TopAiringAdapter
    private lateinit var topUpcomingAdapter: TopUpcomingAdapter
    private lateinit var topTvAdapter: TopTvAdapter
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var topOvaAdapter: TopOvaAdapter
    var listResponse: ArrayList<AnimeListResponse> = ArrayList()

    companion object {
        fun newInstance() = MoreFragment()
    }

    //private lateinit var viewModel: MoreViewModel

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.more_fragment2, container, false)
        val typeFromActivity = MoreFragmentArgs.fromBundle(requireArguments()).type.type
        setView(root,typeFromActivity)
        return root
    }



    @ExperimentalPagingApi
    private fun setView(root: View, type:String) {
        recyclerView = root.findViewById(R.id.rv_top)
        topAiringAdapter = TopAiringAdapter()

        topUpcomingAdapter = TopUpcomingAdapter()
        topTvAdapter = TopTvAdapter()
        topMovieAdapter = TopMovieAdapter()
        topOvaAdapter = TopOvaAdapter()
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager
        when (type) {
            "airing" -> lifecycleScope.launch {
                moreViewModel.getTopAiringAnimes().collectLatest {
                    recyclerView.adapter = topAiringAdapter
                    topAiringAdapter.submitData(it)

                }


            }
            "upcoming" -> lifecycleScope.launch {
                moreViewModel.getTopUpcomingAnimes().collectLatest {
                    recyclerView.adapter = topUpcomingAdapter
                    topUpcomingAdapter.submitData(it)

                }
            }
            "movie" -> lifecycleScope.launch {
                moreViewModel.getTopMovieAnimes().collectLatest {
                    recyclerView.adapter = topMovieAdapter
                    topMovieAdapter.submitData(it)
                }
            }
            "tv" -> lifecycleScope.launch {
                moreViewModel.getTopTvAnimes().collectLatest {
                    recyclerView.adapter = topTvAdapter
                    topTvAdapter.submitData(it)
                }
            }
            "ova" -> lifecycleScope.launch {
                moreViewModel.getTopOvaAnimes().collectLatest {
                    recyclerView.adapter = topOvaAdapter
                    topOvaAdapter.submitData(it)
                }
            }
            else -> { // Note the block
                return
            }
        }


    }

    override fun onItemClick(parentView: View?, childView: View?, position: Int) {
        TODO("Not yet implemented")
    }

}