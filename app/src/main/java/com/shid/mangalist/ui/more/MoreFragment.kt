package com.shid.mangalist.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.databinding.MoreFragment2Binding
import com.shid.mangalist.utils.custom.BaseFragment
import com.shid.mangalist.utils.custom.gone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class MoreFragment : BaseFragment() {
    private val moreViewModel: MoreViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoreAdapter

    private var _binding:MoreFragment2Binding?=null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = MoreFragment()
    }


    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoreFragment2Binding.inflate(inflater,container,false)
        val rootView = binding.root
        val typeFromActivity = MoreFragmentArgs.fromBundle(requireArguments()).type.type
        setView(typeFromActivity)
        return rootView
    }


    @ExperimentalPagingApi
    private fun setView(type: String) {
        moreViewModel.setType(type)
        setRecyclerView()
        setBottomNav()
        fixActionBar()
    }

    private fun setBottomNav() {
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.gone()

    }

    private fun fixActionBar() {
        val view = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view.fitsSystemWindows = false
        view.setPadding(0,0,0,0)
    }

    private fun setRecyclerView() {
        setAdapter()
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        loadDataInRecyclerView()
        binding.rvTop.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)

        }

        setSnapHelper()
    }

    private fun setAdapter() {
        adapter = MoreAdapter(activity) { id -> showDetail(id) }
        binding.rvTop.adapter = adapter
    }


    private fun loadDataInRecyclerView() {
        lifecycleScope.launch {
            moreViewModel.animeAiring.observe(viewLifecycleOwner, { anime ->
                if (anime != null) {
                    adapter.setData(anime)
                }
            })
        }
    }

    private fun setSnapHelper() {
        val snapHelper = com.shid.mangalist.utils.custom.PagerSnapHelper { position ->

            val anime = adapter.getAnimeItem(position)
            (activity as MainActivity).updateBackground(anime.imageUrl)
        }

        snapHelper.attachToRecyclerView(binding.rvTop)
    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(MoreFragmentDirections.actionMoreFragmentToDetailAnimeFragment(id))
    }


}