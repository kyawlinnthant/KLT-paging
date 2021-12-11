package com.example.paging3sample.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RemoteFragment : Fragment(R.layout.remote_fragment) {

    private var adapter: MoviePagingDataAdapter? = null
    private val vm: RemoteViewModel by viewModels()

    private val recyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.remote_recycler)
    }

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

        adapter = MoviePagingDataAdapter { getItemClick(it) }.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = this
            recyclerView.adapter = withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(this),
                footer = MovieLoadStateAdapter(this)
            )
        }
    }

    private fun getItemClick(position: Int) {
        val item = adapter?.getClickItem(position)
        item?.let {
            Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
        }
    }

}

/*

private val loadingLayout by lazy {
    requireActivity().findViewById<ConstraintLayout>(R.id.loading)
}
 private fun shouldShowLoading(isCompleted: Boolean) {
        if (isCompleted) {
            loadingLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            loadingLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
*/
