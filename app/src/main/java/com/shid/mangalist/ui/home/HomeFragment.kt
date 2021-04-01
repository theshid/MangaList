package com.shid.mangalist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.asksira.loopingviewpager.LoopingViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.*
import com.shid.mangalist.data.remote.AnimePagingSource
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.ui.detail.DetailFragment
import com.shid.mangalist.utils.GsonParser
import com.shid.mangalist.utils.custom.RecyclerItemClickListener
import com.shid.mangalist.utils.enum.More
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.addTransformation
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @ExperimentalPagingApi
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var topAiringAdapter: HomeAdapter
    private lateinit var topUpcomingAdapter: HomeAdapter
    private lateinit var topTvAdapter: HomeAdapter
    private lateinit var topMovieAdapter: HomeAdapter
    private lateinit var topOvaAdapter: HomeAdapter

    private lateinit var airingRecyclerView: RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var tvRecyclerView: RecyclerView
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var ovaRecyclerView: RecyclerView

    private lateinit var txt_moreAiring: TextView
    /*private lateinit var txt_moreUpcoming: TextView
    private lateinit var txt_moreTv: TextView
    private lateinit var txt_moreMovie: TextView
    private lateinit var txt_moreOva: TextView*/
    private lateinit var layoutBottomSheet: View
    private lateinit var viewPager: LoopingViewPager
    private lateinit var viewPagerAdapter: LoopingPagerAdapter<AnimeListResponse>

    private lateinit var imgTrending:ImageView
    private lateinit var imgTrending2:ImageView
    private lateinit var imgTrending3:ImageView
    private var img_id:Int?= null
    private var img_id1:Int?= null
    private var img_id2:Int?= null

    private lateinit var layout_trending:LinearLayout
    private lateinit var layout_upcoming:LinearLayout
    private lateinit var layout_movie:LinearLayout
    private lateinit var layout_tv:LinearLayout


    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null


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
        setBottomHomeFragment()
        fetchTopAnimes()

        clickListeners()


        return root
    }

    companion object {
        lateinit var typeAnime: String
    }

    private fun setBottomHomeFragment() {

        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
    }

    private fun clickListeners() {
        txt_moreAiring.setOnClickListener(View.OnClickListener {
            showMore(More.AIRING)
        })

        layout_upcoming.setOnClickListener(View.OnClickListener {
            showMore(More.UPCOMING)
        })

        layout_movie.setOnClickListener(View.OnClickListener {
            showMore(More.MOVIE)
        })

        layout_tv.setOnClickListener(View.OnClickListener {
            showMore(More.TV)
        })

        imgTrending.setOnClickListener(View.OnClickListener {
            img_id?.let { it1 -> showDetail(it1) }
        })

        imgTrending2.setOnClickListener(View.OnClickListener {
            img_id1?.let { it1 -> showDetail(it1) }
        })

        imgTrending3.setOnClickListener(View.OnClickListener {
            img_id2?.let { it1 -> showDetail(it1) }
        })
    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailAnimeFragment(id))
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

        imgTrending = view.findViewById(R.id.img_trending)
        imgTrending2 = view.findViewById(R.id.img_trending2)
        imgTrending3 = view.findViewById(R.id.img_trending3)

        layoutBottomSheet = view.findViewById(R.id.layoutBottomSheet)
        viewPager = view.findViewById(R.id.viewpager)

        layout_movie =view.findViewById(R.id.layout_top_movie)
        layout_upcoming =view.findViewById(R.id.layout_top_upcoming)
        layout_tv =view.findViewById(R.id.layout_top_tv)

        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL


        val linearLayoutManager1 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL


        val linearLayoutManager2 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL


        val linearLayoutManager3 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL


        val linearLayoutManager4 = ZoomRecyclerLayout(requireContext())
        linearLayoutManager4.orientation = LinearLayoutManager.HORIZONTAL



        airingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_airing)
        airingRecyclerView.layoutManager = linearLayoutManager
        topAiringAdapter = HomeAdapter { id -> showDetail(id) }
        airingRecyclerView.adapter = topAiringAdapter

        upcomingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_upcoming)
        upcomingRecyclerView.layoutManager = linearLayoutManager1
        topUpcomingAdapter = HomeAdapter { id -> showDetail(id) }
        upcomingRecyclerView.adapter = topUpcomingAdapter

        tvRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_tv)
        tvRecyclerView.layoutManager = linearLayoutManager2
        topTvAdapter = HomeAdapter { id -> showDetail(id) }
        tvRecyclerView.adapter = topTvAdapter

        movieRecyclerView = view.findViewById<RecyclerView>(R.id.rv_top_movie2)
        movieRecyclerView.layoutManager = linearLayoutManager3
        topMovieAdapter = HomeAdapter { id -> showDetail(id) }
        movieRecyclerView.adapter = topMovieAdapter

    }

    @ExperimentalPagingApi
    private fun fetchTopAnimes() {
        lifecycleScope.launch {
            homeViewModel.animeAiring.observe(viewLifecycleOwner, { anime ->
                if (anime.isNotEmpty()) {
                    topAiringAdapter.setData(anime)
                    imgTrending.load(anime[0].imageUrl)
                    imgTrending2.load(anime[1].imageUrl)
                    imgTrending3.load(anime[2].imageUrl)
                    img_id = anime[0].id
                    img_id1 = anime[1].id
                    img_id2 = anime[2].id

                }
            })


        }
        lifecycleScope.launch {
            homeViewModel.animeUpcoming.observe(viewLifecycleOwner, { anime ->
                if (anime.isNotEmpty()) {
                    topUpcomingAdapter.setData(anime)
                }
            })
        }

        lifecycleScope.launch {
            homeViewModel.animeMovie.observe(viewLifecycleOwner, { anime ->
                if (anime.isNotEmpty()) {
                    topMovieAdapter.setData(anime)
                }
            })
        }

        lifecycleScope.launch {
            homeViewModel.animeTV.observe(viewLifecycleOwner, { anime ->
                if (anime.isNotEmpty()) {
                    topTvAdapter.setData(anime)
                }
            })
        }

        lifecycleScope.launch {
            homeViewModel.animeOva.observe(viewLifecycleOwner, { anime ->
                if (anime.isNotEmpty()) {
                    //topOvaAdapter.setData(anime)
                    viewPagerAdapter = LoopAnimeAdapter(
                        requireContext(),
                        anime as ArrayList<AnimeListResponse>, true
                    ) { id -> showDetail(id) }
                    viewPager.adapter = viewPagerAdapter
                }
            })
        }


    }

    override fun onResume() {
        super.onResume()
        viewPager.resumeAutoScroll()
    }

    override fun onPause() {
        viewPager.pauseAutoScroll()
        super.onPause()
    }
}