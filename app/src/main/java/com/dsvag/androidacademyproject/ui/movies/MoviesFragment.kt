package com.dsvag.androidacademyproject.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsvag.androidacademyproject.databinding.FragmentMoviesBinding
import com.dsvag.androidacademyproject.utils.ItemDecoration
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val moviesViewModel: MoviesViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Now Playing"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Popular"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Top Rated"))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })


        moviesViewModel.resultData.observe(viewLifecycleOwner) { movieList ->
            movieList?.let {
                movieAdapter.setData(it)
            }
        }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}