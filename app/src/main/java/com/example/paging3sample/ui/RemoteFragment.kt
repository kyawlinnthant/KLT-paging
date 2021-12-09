package com.example.paging3sample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import com.example.paging3sample.helper.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RemoteFragment : Fragment(R.layout.remote_fragment) {

    private var adapter: MovieListAdapter? = null
    private val vm: RemoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observe()
    }

    private fun initUi() {
        vm.getMovies()
        setUpRecyclerView()
    }

    private fun observe() {
        vm.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Timber.tag("loading").d("this is loading")
                }
                Resource.Status.SUCCESS -> {

                    Timber.tag("success").d(it.data!!.results.size.toString())
                    adapter?.submitList(it.data.results)

                }
                Resource.Status.ERROR -> {

                    Timber.tag("error").d(it.message)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        val v = requireActivity().findViewById<RecyclerView>(R.id.remote_recycler)
        adapter = MovieListAdapter { getItemClick(it) }.apply {
            v.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            v.adapter = this
        }
    }

    private fun getItemClick(position: Int) {
        val item = adapter?.getClickItem(position)
        Timber.e(item.toString())
    }

}