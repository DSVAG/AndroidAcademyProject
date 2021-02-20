package com.dsvag.androidacademyproject.ui.moviedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.GrayscaleTransformation
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.FragmentMovieDetailsBinding
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.ui.MainActivity
import com.dsvag.androidacademyproject.ui.viewBinding
import com.dsvag.androidacademyproject.utils.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val movieViewModel: MovieViewModel by viewModels()

    private val castAdapter by lazy { CastAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.castList.addItemDecoration(ItemDecoration(16f))
        binding.castList.adapter = castAdapter

        movieViewModel.state.observe(viewLifecycleOwner, ::stateObserver)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        val movieId = arguments?.getLong("movieId") ?: 0

        movieViewModel.fetchMovie(movieId)
    }

    private fun stateObserver(state: MovieViewModel.State) {
        when (state) {
            MovieViewModel.State.Loading -> {
                binding.container.isVisible = false
                setLoading(true)
            }
            is MovieViewModel.State.Error -> {
                Toast.makeText(requireContext(), state.msg, Toast.LENGTH_LONG).show()
                setLoading(false)
            }
            is MovieViewModel.State.Success -> {
                setMovieData(state.movie)
                castAdapter.setData(state.movie.cast)
            }
        }
    }

    private fun setMovieData(movie: Movie) {
        val url = "https://image.tmdb.org/t/p/w1280" + movie.backdropPath

        binding.backdrop.load(url) {
            crossfade(true)
            transformations(GrayscaleTransformation())
        }

        binding.title.text = movie.title
        binding.genres.text = movie.genres.joinToString { it.name }
        binding.rating.rating = movie.voteAverage.toFloat() / 2
        binding.review.text = movie.voteCount.toString().plus(" Reviews")
        binding.storyline.text = movie.overview

        binding.container.isVisible = true
        setLoading(false)
    }


    private fun setLoading(visibility: Boolean) {
        (activity as MainActivity?)?.loadingVisibility(visibility)
    }
}