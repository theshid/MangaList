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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.AiringAnime
import com.shid.mangalist.ui.detail.DetailFragment
import com.shid.mangalist.utils.enum.More
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.addTransformation
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class HomeFragment : Fragment(),TopAiringAdapter.AnimeDelegate {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onTransformationStartContainer()
    }

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
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
        (activity as MainActivity).clearBackground()
        txt_moreAiring = view.findViewById(R.id.more_airing)
        txt_moreUpcoming = view.findViewById(R.id.more_upcoming)
        txt_moreTv = view.findViewById(R.id.more_tv)
        txt_moreMovie = view.findViewById(R.id.more_movie)
        txt_moreOva = view.findViewById(R.id.more_ova)

        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        val linearLayoutManager1 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager1.reverseLayout = true
        linearLayoutManager1.stackFromEnd = true

        val linearLayoutManager2 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager2.reverseLayout = true
        linearLayoutManager2.stackFromEnd = true


        val linearLayoutManager3 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager3.reverseLayout = true
        linearLayoutManager3.stackFromEnd = true


        val linearLayoutManager4 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager4.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager4.reverseLayout = true
        linearLayoutManager4.stackFromEnd = true

        



        airingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_airing)
        airingRecyclerView.layoutManager = linearLayoutManager
        topAiringAdapter = TopAiringAdapter(this)
        airingRecyclerView.adapter = topAiringAdapter

        upcomingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_upcoming)
        upcomingRecyclerView.layoutManager = linearLayoutManager1
        topUpcomingAdapter = TopUpcomingAdapter()
        upcomingRecyclerView.adapter = topUpcomingAdapter

        tvRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_tv)
        tvRecyclerView.layoutManager = linearLayoutManager2
        topTvAdapter = TopTvAdapter()
        tvRecyclerView.adapter = topTvAdapter

        movieRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_movie)
        movieRecyclerView.layoutManager = linearLayoutManager3
        topMovieAdapter = TopMovieAdapter()
        movieRecyclerView.adapter = topMovieAdapter

        ovaRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_ova)
        ovaRecyclerView.layoutManager = linearLayoutManager4
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

    override fun onItemClick(airingAnime: AiringAnime, itemView: TransformationLayout) {
        val fragment = DetailFragment()
        // [Step2]: getBundle from the TransformationLayout.
        val bundle = itemView.getBundle(DetailFragment.paramsKey)
        bundle.putParcelable(DetailFragment.posterKey, airingAnime)
        airingAnime.id?.let { bundle.putInt("key", it) }
        fragment.arguments = bundle

        parentFragmentManager
            .beginTransaction()
            // [Step3]: addTransformation using the TransformationLayout.
            .addTransformation(itemView)
            .replace(R.id.nav_host_fragment, fragment, DetailFragment.TAG)
            .addToBackStack(DetailFragment.TAG)
            .commit()
    }
}