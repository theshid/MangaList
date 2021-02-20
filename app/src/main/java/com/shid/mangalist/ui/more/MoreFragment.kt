package com.shid.mangalist.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.utils.custom.BaseFragment
import com.shid.mangalist.utils.custom.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment(), RecyclerItemClickListener.OnRecyclerViewItemClickListener {
    private val moreViewModel: MoreViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoreAdapter

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
        setView(root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MoreViewModel::class.java)


    }

    private fun setView(root: View) {
        recyclerView = root.findViewById(R.id.rv_top)
        adapter = MoreAdapter(activity)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, this))

        val snapHelper = PagerSnapHelper({ position ->
            val movie: AnimeListResponse = adapter.getItem(position)
            (activity as MainActivity).updateBackground(movie.imageUrl)
        })
    }

    override fun onItemClick(parentView: View?, childView: View?, position: Int) {
        TODO("Not yet implemented")
    }

}