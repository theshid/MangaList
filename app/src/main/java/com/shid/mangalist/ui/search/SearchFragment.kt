package com.shid.mangalist.ui.search

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.ui.discover.DiscoverAdapter
import com.shid.mangalist.ui.discover.DiscoverFragmentDirections
import com.shid.mangalist.ui.discover.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @ExperimentalPagingApi
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var adapter: DiscoverAdapter
    private lateinit var rvAnimeSearch: RecyclerView
    private lateinit var loading: View
    private lateinit var lottie_search:LottieAnimationView

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.search_fragment, container, false)
        val view = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view.fitsSystemWindows = false
        view.setPadding(0,0,0,0)
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE
        loading = root.findViewById(R.id.loading)
        lottie_search = root.findViewById(R.id.lottie_search)
        rvAnimeSearch = root.findViewById(R.id.searchRecycler)
        adapter = DiscoverAdapter { id -> showDetail(id) }
        rvAnimeSearch.adapter = adapter
        with(rvAnimeSearch) {
            setHasFixedSize(true)
            adapter = adapter
        }
        return root
    }

    @ExperimentalPagingApi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("SetTextI18n")
            override fun onQueryTextSubmit(query: String?): Boolean {
                loading.visibility = View.VISIBLE
                lottie_search.visibility = View.GONE

                if (query != null) {
                    searchViewModel.setResult(query)
                    searchViewModel.animeResult.observe(viewLifecycleOwner, { anime ->
                        if (anime.isNotEmpty()) {
                            adapter.setData(anime)
                            loading.visibility = View.GONE
                        } else {
                            lottie_search.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "Oops, no anime found! Try to type another name", Toast.LENGTH_SHORT).show()
                            /*binding.textOnSearch.text =
                                "Ups, no anime found! Try to type another name :)"*/
                        }
                    })
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(SearchFragmentDirections.actionSearchFragmentToDetailAnimeFragment(id))
    }



}