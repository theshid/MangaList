package com.shid.mangalist.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shid.mangalist.MainActivity
import com.shid.mangalist.R
import com.shid.mangalist.databinding.FragmentBookmarkBinding
import com.shid.mangalist.ui.discover.DiscoverFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    @ExperimentalPagingApi
    private val bookmarkViewModel: BookmarksViewModel by viewModels()
    private lateinit var adapter: BookmarkAdapter

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val view1 = (activity as MainActivity).findViewById<ConstraintLayout>(R.id.container)
        view1.fitsSystemWindows = false
        view1.setPadding(0,0,0,0)
        val view: View = binding.root
        // val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
        initViews()
        setHasOptionsMenu(true)
        return view
    }

    @ExperimentalPagingApi
    private fun initViews() {
        val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
        adapter = BookmarkAdapter { id -> showDetail(id) }
        _binding?.rvBookmark?.adapter = adapter
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                _binding?.lottieEmpty!!.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
            }
        })

        bookmarkViewModel.getAllBookmarks()
        bookmarkViewModel.bookmarkList.observe(viewLifecycleOwner, Observer {
              if (it.isNotEmpty()){
                  adapter.setData(it)
              }
        })

    }

    private fun showDetail(id: Int) {
        this.findNavController()
            .navigate(BookmarksFragmentDirections.actionBookmarkFragmentToDetailAnimeFragment(id))
    }
}