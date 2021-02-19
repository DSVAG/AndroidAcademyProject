package com.dsvag.androidacademyproject.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.FragmentMoviesBinding
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.ui.MainActivity
import com.dsvag.androidacademyproject.ui.viewBinding
import com.dsvag.androidacademyproject.utils.ItemDecoration
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding(FragmentMoviesBinding::bind)

    private val moviesViewModel: MoviesViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity?)?.setBottomViewVisibility(true)

        binding.movieList.adapter = movieAdapter
        binding.movieList.addItemDecoration(ItemDecoration(16f))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                movieAdapter.setData(emptyList())
                when (tab?.position) {
                    0 -> moviesViewModel.fetchNowPlaying()
                    1 -> moviesViewModel.fetchPopular()
                    2 -> moviesViewModel.fetchTopRated()
                    else -> error("unknown position ${tab?.position}")
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.movieList.layoutManager?.scrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        binding.movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == movieAdapter.itemCount - 1) {
                        moviesViewModel.nextPage()
                    }
                }
            }
        })

        moviesViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                MoviesViewModel.State.Default -> moviesViewModel.fetchNowPlaying()
                MoviesViewModel.State.Loading -> setLoading(true)
                is MoviesViewModel.State.Success -> isSuccess(state.movies)
                is MoviesViewModel.State.Error -> showError(state.msg)
            }
        }
    }

    private fun isSuccess(movies: List<Movie>) {
        movieAdapter.setData(movies)
        setLoading(false)
    }

    private fun setLoading(visibility: Boolean) {
        // binding.loadingIndicator.isVisible = visibility
    }

    private fun showError(msg: String) {
        setLoading(false)
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}