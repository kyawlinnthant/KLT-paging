package com.example.paging3sample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RemoteFragment : Fragment(R.layout.remote_fragment) {

    private var adapter: MoviePagingDataAdapter? = null
    private val vm: RemoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observe()
    }

    private fun initUi() {

        setUpRecyclerView()
    }

    private fun observe() {

        with(vm) {

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                movies.collect {
                    adapter?.submitData(it)
                }
            }

        }
    }


    private fun setUpRecyclerView() {
        val v = requireActivity().findViewById<RecyclerView>(R.id.remote_recycler)
        adapter = MoviePagingDataAdapter { getItemClick(it) }.apply {
            v.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            v.adapter = this
            v.adapter = withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(this),
                footer = MovieLoadStateAdapter(this)
            )
        }
    }

    private fun getItemClick(position: Int) {
        val item = adapter?.getClickItem(position)
        Timber.e(item.toString())
    }

}