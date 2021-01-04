package com.dsvag.androidacademyproject.ui.fragments.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dsvag.androidacademyproject.databinding.FragmentMovieDetailsBinding
import com.dsvag.androidacademyproject.models.Movie
import com.dsvag.androidacademyproject.utils.ItemDecoration

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val castAdapter by lazy { ActorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movie = requireArguments().getParcelable<Movie>("movie")!!

        binding.castList.setHasFixedSize(true)
        binding.castList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.castList.adapter = castAdapter

        binding.castList.addItemDecoration(ItemDecoration(8))

        setData(movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setData(movie: Movie) {
        binding.preview.clipToOutline = true

        binding.name.text = movie.title
        binding.ageLimit.text = movie.minimumAge.toString().plus("+")
        binding.tags.text = movie.genres.joinToString(", ") { it.name }
        binding.review.text = movie.numberOfRatings.toString().plus(" Reviews")
        binding.rating.rating = movie.ratings / 2
        binding.storyline.text = movie.overview
        binding.ageLimit.text = movie.minimumAge.toString().plus('+')
        castAdapter.setData(movie.actors)

        Glide
            .with(this)
            .load(movie.backdrop)
            .optionalFitCenter()
            .into(binding.preview)
    }
}