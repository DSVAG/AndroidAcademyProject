package com.dsvag.androidacademyproject.ui.fragments.movielist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dsvag.androidacademyproject.databinding.FragmentMovieListBinding
import com.dsvag.androidacademyproject.models.Movie
import com.dsvag.androidacademyproject.ui.adapters.ItemDecoration
import com.dsvag.androidacademyproject.ui.adapters.MovieAdapter
import com.dsvag.androidacademyproject.ui.viewmodels.MoviesViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter by lazy { MovieAdapter() }

    private val moviesViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, MoviesViewModel.Factory(requireActivity().application))
            .get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.moveList.setHasFixedSize(true)

        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.moveList.layoutManager = GridLayoutManager(requireContext(), 4)
        } else {
            binding.moveList.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        binding.moveList.adapter = movieAdapter

        binding.moveList.addItemDecoration(ItemDecoration(8))

        moviesViewModel.fetchMovies().observe(viewLifecycleOwner) { movieList: List<Movie>? ->
            if (movieList != null) {
                movieAdapter.setData(movieList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}