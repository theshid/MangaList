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
import com.shid.mangalist.databinding.SearchFragmentBinding
import com.shid.mangalist.ui.discover.DiscoverAdapter
import com.shid.mangalist.ui.discover.DiscoverFragmentDirections
import com.shid.mangalist.ui.discover.DiscoverViewModel
import com.shid.mangalist.utils.custom.gone
import com.shid.mangalist.utils.custom.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @ExperimentalPagingApi
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var adapter: DiscoverAdapter
    private lateinit var loading: View
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root
        setHasOptionsMenu(true)
        setUI()
        loading = rootView.findViewById(R.id.loading)
        return rootView
    }

    private fun setUI() {
        setRecyclerView()
        setBottomNav()
        fixActionBar()
    }

    private fun setRecyclerView() {
        adapter = DiscoverAdapter { id -> showDetail(id) }
        _binding?.searchRecycler?.adapter = adapter
        with(binding.searchRecycler) {
            setHasFixedSize(true)
            adapter = adapter
        }
    }

    private fun fixActionBar() {
        val view = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view.fitsSystemWindows = false
        view.setPadding(0, 0, 0, 0)
    }

    private fun setBottomNav() {
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.gone()
    }


    @ExperimentalPagingApi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("SetTextI18n")
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.apply {
                    lottieSearch.gone()
                }
                loading.visible()
                //lottie_search.visibility = View.GONE

                if (query != null) {
                    searchViewModel.setResult(query)
                    searchViewModel.animeResult.observe(viewLifecycleOwner, { anime ->
                        if (anime.isNotEmpty()) {
                            adapter.setData(anime)
                            loading.gone()
                        } else {
                            binding.lottieSearch.visible()
                            //lottie_search.visibility = View.VISIBLE
                            Toast.makeText(
                                requireContext(),
                                "Oops, no anime found! Try to type another name",
                                Toast.LENGTH_SHORT
                            ).show()
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