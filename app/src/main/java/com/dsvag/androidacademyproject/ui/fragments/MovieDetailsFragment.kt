package com.dsvag.androidacademyproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.data.adapters.ActorAdapter
import com.dsvag.androidacademyproject.data.adapters.ItemDecoration
import com.dsvag.androidacademyproject.data.models.Movie
import com.dsvag.androidacademyproject.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val castAdapter by lazy { ActorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        binding.castList.setHasFixedSize(true)
        binding.castList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.castList.adapter = castAdapter

        binding.castList.addItemDecoration(ItemDecoration(10))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val maybeMovie = arguments?.getParcelable<Movie>("movie")

        if (maybeMovie != null) {
            setData(maybeMovie)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setData(movie: Movie) {
        binding.name.text = movie.name
        binding.tags.text = movie.tags.joinToString(", ")
        binding.rating.rating = movie.rating.toFloat()
        binding.review.text = movie.reviews.toString().plus(" Reviews")
        binding.storyline.text = movie.storyLine
        binding.ageLimit.text = movie.ageLimit.toString().plus('+')
        castAdapter.setData(movie.cast)

        Glide
            .with(this)
            .load(requireContext().getDrawable(R.drawable.pic_avengers2)!!.toBitmap())
            .optionalCenterInside()
            .into(binding.preview)
    }
}