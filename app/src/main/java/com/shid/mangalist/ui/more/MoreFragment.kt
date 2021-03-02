package com.shid.mangalist.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.ui.detail.DetailFragment
import com.shid.mangalist.ui.home.*
import com.shid.mangalist.utils.custom.BaseFragment
import com.shid.mangalist.utils.custom.RecyclerItemClickListener
import com.shid.mangalist.utils.custom.RecyclerSnapItemListener
import com.shid.mangalist.utils.enum.More
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.addTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class MoreFragment : BaseFragment(), MoreAdapter.AnimeDelegate{
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
        recyclerView = root.findViewById(R.id.rv_top)
        adapter = MoreAdapter(activity,this)
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = adapter
        }

        var snapHelper = com.shid.mangalist.utils.custom.PagerSnapHelper { position ->

            var anime = adapter.getAnimeItem(position)
            (activity as MainActivity).updateBackground(anime.imageUrl)
        }

        snapHelper.attachToRecyclerView(recyclerView)


        lifecycleScope.launch {
            moreViewModel.animes.collectLatest {
                recyclerView.adapter = adapter
                adapter.submitData(it)
            }
        }


    }

    fun goToHome() {
        findNavController().navigate(
            MoreFragmentDirections.actionMoreFragmentToHomeFragment()
        )
    }



    override fun onItemClick(anime: AnimeListResponse, itemView: TransformationLayout) {
        val fragment = DetailFragment()
        // [Step2]: getBundle from the TransformationLayout.
        val bundle = itemView.getBundle(DetailFragment.paramsKey)
        // bundle.putParcelable(DetailFragment.posterKey3, upcomingAnime)
        anime.id?.let { bundle.putInt("key", it) }
        anime.imageUrl?.let { bundle.putString("key2",it) }
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