package com.dsvag.androidacademyproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dsvag.androidacademyproject.data.adapters.ItemDecoration
import com.dsvag.androidacademyproject.data.adapters.MovieAdapter
import com.dsvag.androidacademyproject.data.utils.loadMovies
import com.dsvag.androidacademyproject.databinding.FragmentMovieListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.moveList.setHasFixedSize(true)
        binding.moveList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.moveList.adapter = movieAdapter

        binding.moveList.addItemDecoration(ItemDecoration(8))

        lifecycleScope.launch(Dispatchers.Main) {
            movieAdapter.setData(loadMovies(requireContext()))
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}