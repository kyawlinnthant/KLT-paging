package com.example.paging3sample.ui.detail

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.paging3sample.R
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val vm : DetailViewModel by viewModels()
    private val args : DetailFragmentArgs by navArgs()

    private val img by lazy {
        requireActivity().findViewById<ImageView>(R.id.img_detail)
    }
    private val title by lazy {
        requireActivity().findViewById<TextView>(R.id.tv_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setExpendToolbar(true)
        initUi()
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.movie.observe(viewLifecycleOwner){
                when(it.status){
                    Resource.Status.LOADING ->{
                        //do some logic
                    }
                    Resource.Status.ERROR ->{
                        //do some logic
                    }
                    Resource.Status.SUCCESS ->{
                        setupView(it.data!!)
                    }
                }
            }
        }
    }

    private fun initUi() {
        vm.getMovieDetail(args.id)
    }

    private fun setupView(movie: DetailResponse) {

        val placeholder = if (isDarkTheme(requireActivity()))R.drawable.placeholder_dark else R.drawable.place_holder

        title.text = movie.original_title

        Glide.with(requireContext())
            .load(Endpoints.IMAGE_URL + movie.backdrop_path)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(placeholder)
            .into(this.img)

    }
    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}