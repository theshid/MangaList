package com.shid.mangalist.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.utils.custom.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class MoreFragment : BaseFragment() {
    private val moreViewModel: MoreViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoreAdapter


    companion object {
        fun newInstance() = MoreFragment()
    }


    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.more_fragment2, container, false)
        val typeFromActivity = MoreFragmentArgs.fromBundle(requireArguments()).type.type
        setView(root, typeFromActivity)
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            goToHome()
        }
        callback.isEnabled*/

    }


    @ExperimentalPagingApi
    private fun setView(root: View, type: String) {
        moreViewModel.setType(type)
        recyclerView = root.findViewById(R.id.rv_top)
        adapter = MoreAdapter(activity) { id -> showDetail(id) }
        recyclerView.adapter = adapter
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        lifecycleScope.launch {
            moreViewModel.animeAiring.observe(viewLifecycleOwner, { anime ->
                if (anime != null) {
                    adapter.setData(anime)
                }
            })
        }
        recyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)

        }

        var snapHelper = com.shid.mangalist.utils.custom.PagerSnapHelper { position ->

            var anime = adapter.getAnimeItem(position)
            (activity as MainActivity).updateBackground(anime.imageUrl)
        }

        snapHelper.attachToRecyclerView(recyclerView)





    }

    fun goToHome() {
        findNavController().navigate(
            MoreFragmentDirections.actionMoreFragmentToHomeFragment()
        )
    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(MoreFragmentDirections.actionMoreFragmentToDetailAnimeFragment(id))
    }


}