package com.shid.mangalist.ui.discover

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.utils.custom.BaseFragment
import com.shid.mangalist.utils.enum.Season
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class DiscoverFragment : BaseFragment() {

    @ExperimentalPagingApi
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private lateinit var adapter: DiscoverAdapter
    private var thisYear = 0
    private lateinit var loading: View
    private lateinit var rvAnimeSeason: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_discovery, container, false)
        val view = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view.fitsSystemWindows = false
        view.setPadding(0,0,0,0)
        initViews(root)
        setHasOptionsMenu(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thisYear = Calendar.getInstance()[Calendar.YEAR]
    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(DiscoverFragmentDirections.actionNavigationDiscoverToDetailAnimeFragment(id))
    }

    @ExperimentalPagingApi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.season_menu, menu)
        loading.visibility = View.VISIBLE
        discoverViewModel.setSeason(thisYear, Season.SPRING.value.toLowerCase())
        setTitleSeason(Season.SPRING)
        discoverViewModel.animeSeason.observe(viewLifecycleOwner, { anime ->
            if (anime.isNotEmpty()) {
                adapter.setData(anime)
                loading.visibility = View.GONE
            }
        })
        with(rvAnimeSeason) {
            setHasFixedSize(true)
            adapter = adapter
        }
    }

    @ExperimentalPagingApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_spring -> refreshList(Season.SPRING)
            R.id.menu_summer -> refreshList(Season.SUMMER)
            R.id.menu_fall -> refreshList(Season.FALL)
            R.id.menu_winter -> refreshList(Season.WINTER)
        }

        return super.onOptionsItemSelected(item)
    }

    @ExperimentalPagingApi
    private fun refreshList(season: Season) {
        discoverViewModel.setSeason(thisYear, season.value.toLowerCase())
        setTitleSeason(season)
        discoverViewModel.animeSeason.observe(viewLifecycleOwner, { anime ->
            if (anime.isNotEmpty()) {
                adapter.setData(anime)
            }
        })
    }

    private fun setTitleSeason(season: Season) {
        val icon: String
        when (season) {
            Season.SPRING -> {
                icon = String(Character.toChars(0x1F331))
                (activity as AppCompatActivity).supportActionBar?.title =
                    "${Season.SPRING.value}$icon ~ $thisYear"
            }
            Season.SUMMER -> {
                icon = String(Character.toChars(0x1F31E))
                (activity as AppCompatActivity).supportActionBar?.title =
                    "${Season.SUMMER.value}$icon ~ $thisYear"
            }
            Season.FALL -> {
                icon = String(Character.toChars(0x1F342))
                (activity as AppCompatActivity).supportActionBar?.title =
                    "${Season.FALL.value}$icon ~ $thisYear"
            }
            Season.WINTER -> {
                icon = String(Character.toChars(0x2744))
                (activity as AppCompatActivity).supportActionBar?.title =
                    "${Season.WINTER.value}$icon ~ $thisYear"
            }
        }
    }

    private fun initViews(view: View) {
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
        loading = view.findViewById(R.id.loading)
        rvAnimeSeason = view.findViewById(R.id.rv_anime_season)
        adapter = DiscoverAdapter { id -> showDetail(id) }
        rvAnimeSeason.adapter = adapter
    }
}