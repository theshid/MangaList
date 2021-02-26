package com.shid.mangalist.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import coil.load
import com.shid.mangalist.R
import com.shid.mangalist.data.local.entities.AiringAnime
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
 lateinit var rootView:ScrollView
 lateinit var trans_imageView: ImageView
    companion object {
        fun newInstance() = DetailFragment()
        const val TAG = "LibraryFragment"
        const val posterKey = "posterKey"
        const val paramsKey = "paramsKey"
    }

    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = arguments?.getParcelable<TransformationLayout.Params>(paramsKey)
        onTransformationEndContainer(params )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.detail_fragment, container, false)
        rootView = view.findViewById(R.id.root_detail)
        trans_imageView = view.findViewById(R.id.poster_image)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val poster = arguments?.getParcelable<AiringAnime>(posterKey)
        poster?.let {

            rootView.transitionName = it.title

            // [Step2]: sets a transition name to the target view.
            trans_imageView.load(it.imageUrl)

            /*Glide.with(this)
                .load(it.poster)
                .into(binding.profileDetailBackground)
            binding.detailTitle.text = it.name
            binding.detailDescription.text = it.description*/
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}