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
import timber.log.Timber

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {

    private var adapter: MoviePagingDataAdapter? = null
    private val vm: ListViewModel by viewModels()

    private val recyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.popular_recycler)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observe()
    }

    private fun initUi() {
        setUpRecyclerView()
        vm.getPagerMovies(ApiDataSourceImpl.POPULAR)
    }

    private fun observe() {


        vm.movies.observe(viewLifecycleOwner) {
            Timber.tag("popular").d(it.toString())
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                adapter?.submitData(it)
            }

        }

    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setUpRecyclerView() {

        adapter =
            MoviePagingDataAdapter(isDarkTheme(requireActivity())) { getItemClick(it) }.apply {
                this.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                recyclerView.layoutManager = GridLayoutManager(
                    requireContext(), 2,
                    GridLayoutManager.VERTICAL, false
                )
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = this
                recyclerView.adapter = withLoadStateHeaderAndFooter(
                    header = MovieLoadStateAdapter(this),
                    footer = MovieLoadStateAdapter(this)
                )
            }
    }

    private fun getItemClick(position: Int) {
        val item = adapter?.getClickItem(position)
        val directions = PopularFragmentDirections.actionPopularToDetail(item?.id ?: 0L)
        findNavController().navigate(directions)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

}