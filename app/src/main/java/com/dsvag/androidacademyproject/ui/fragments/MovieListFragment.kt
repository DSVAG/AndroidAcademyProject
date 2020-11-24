package com.dsvag.androidacademyproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.data.adapters.MovieAdapter
import com.dsvag.androidacademyproject.data.models.Actor
import com.dsvag.androidacademyproject.data.models.Movie
import com.dsvag.androidacademyproject.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter by lazy { MovieAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        movieAdapter.setData(generateMovies())

        binding.moveList.setHasFixedSize(true)
        binding.moveList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.moveList.adapter = movieAdapter

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun generateMovies() = listOf(
        Movie(
            "Avengers: End Game",
            requireContext().getDrawable(R.drawable.pic_avengers)!!,
            requireContext().getDrawable(R.drawable.pic_avengers2)!!,
            listOf("Action", "Adventure", "Drama"),
            13,
            4.2,
            125,
            requireContext().getString(R.string.story_line_avengers),
            generateActor(),
            137
        ),
    )

    private fun generateActor() = listOf(
        Actor(
            "Robert",
            "Downey Jr.",
            requireContext().getDrawable(R.drawable.pic_downey)!!,
        ),
        Actor(
            "Chris",
            "Evans",
            requireContext().getDrawable(R.drawable.pic_evans)!!,
        ),
        Actor(
            "Mark",
            "Ruffalo",
            requireContext().getDrawable(R.drawable.pic_ruffalo)!!,
        ),
        Actor(
            "Chris",
            "Hemsworth",
            requireContext().getDrawable(R.drawable.pic_hemsworth)!!,
        ),
    )
}