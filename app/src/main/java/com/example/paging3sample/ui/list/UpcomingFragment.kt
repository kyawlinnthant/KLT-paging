package com.example.paging3sample.ui.list

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import com.example.paging3sample.data.ws.ApiDataSourceImpl
import com.example.paging3sample.ui.MovieLoadStateAdapter
import com.example.paging3sample.ui.MoviePagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {

    private var adapter: MoviePagingDataAdapter? = null

    private val vm: ListViewModel by viewModels()

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
        vm.getPagerMovies(ApiDataSourceImpl.UPCOMING)
    }

    private fun observe() {

        vm.movies.observe(viewLifecycleOwner) {

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                adapter?.submitData(it)
            }
        }

    }

    private fun setUpRecyclerView() {

        adapter = MoviePagingDataAdapter(isDarkTheme(requireActivity())) {
            getItemClick(it)
        }.apply {
            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = this
            recyclerView.adapter = withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(this),
                footer = MovieLoadStateAdapter(this)
            )
        }
    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun getItemClick(position: Int) {
        val item = adapter?.getClickItem(position)
        val directions = UpcomingFragmentDirections.actionUpcomingToDetail(item?.id ?: 0L)
        findNavController().navigate(directions)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
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
