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
import com.shid.mangalist.databinding.FragmentDiscoveryBinding
import com.shid.mangalist.utils.custom.BaseFragment
import com.shid.mangalist.utils.custom.gone
import com.shid.mangalist.utils.custom.visible
import com.shid.mangalist.utils.enum.Season
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.properties.Delegates

@ExperimentalPagingApi
@AndroidEntryPoint
class DiscoverFragment : BaseFragment() {

    private val discoverViewModel: DiscoverViewModel by viewModels()
    private lateinit var adapter: DiscoverAdapter
    private var thisYear = 0
    private lateinit var loading: View

    private var _binding: FragmentDiscoveryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoveryBinding.inflate(inflater, container, false)
        val rootView = binding.root
        initViews(rootView)
        setHasOptionsMenu(true)

        return rootView
    }

    private fun initViews(view: View) {
        setBottomNav()
        fixActionActionBar()
        loading = view.findViewById(R.id.loading)
        setAdapter()
    }

    private fun setAdapter() {
        adapter = DiscoverAdapter { id -> showDetail(id) }
        binding.rvAnimeSeason.adapter = adapter
    }

    private fun fixActionActionBar() {
        val view = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view.fitsSystemWindows = false
        view.setPadding(0, 0, 0, 0)
    }

    private fun setBottomNav() {
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisYear = Calendar.getInstance()[Calendar.YEAR]
    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(DiscoverFragmentDirections.actionNavigationDiscoverToDetailAnimeFragment(id))
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.season_menu, menu)
        loading.visible()
        setTitleSeason(Season.SPRING)
        setData()

    }

    private fun setData() {
        discoverViewModel.setSeason(thisYear, Season.SPRING.value.toLowerCase(Locale.ROOT))

        discoverViewModel.animeSeason.observe(viewLifecycleOwner, { anime ->
            if (anime.isNotEmpty()) {
                adapter.setData(anime)
                loading.gone()
            }
        })
        with(binding.rvAnimeSeason) {
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


    private fun refreshList(season: Season) {
        discoverViewModel.setSeason(thisYear, season.value.toLowerCase(Locale.ROOT))
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


}