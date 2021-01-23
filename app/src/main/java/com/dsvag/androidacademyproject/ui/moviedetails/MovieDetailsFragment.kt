package com.dsvag.androidacademyproject.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.GrayscaleTransformation
import com.dsvag.androidacademyproject.databinding.FragmentMovieDetailsBinding
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.utils.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModels()

    private val castAdapter by lazy { CastAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.castList.addItemDecoration(ItemDecoration(16f))
        binding.castList.adapter = castAdapter

        movieViewModel.movieData.observe(viewLifecycleOwner) { movie ->
            movie?.let { setMovieData(it) }
        }

        movieViewModel.castData.observe(viewLifecycleOwner) { cast ->
            cast?.let { castAdapter.setData(it) }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val movieId = arguments?.getInt("movieId") ?: 0

        movieViewModel.fetchMovie(movieId)
        movieViewModel.fetchCredits(movieId)
    }

    private fun setMovieData(movie: Movie) {
        val url = "https://image.tmdb.org/t/p/w1280" + movie.backdropPath

        binding.backdrop.load(url) {
            crossfade(true)
            transformations(GrayscaleTransformation())
        }

        binding.title.text = movie.title
        binding.genres.text = movie.genres.joinToString(", ") { it.name }
        binding.rating.rating = movie.voteAverage.toFloat() / 2
        binding.review.text = movie.voteCount.toString().plus(" Reviews")
        binding.storyline.text = movie.overview
    }
}