package com.dsvag.androidacademyproject.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.RowMovieBinding
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.ui.movies.utils.MovieDiffUtilsCallback

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(RowMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply { putLong("movieId", movies[position].id) }
            holder.itemView.findNavController()
                .navigate(R.id.action_moviesFragment_to_movieDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun setData(newData: List<Movie>) {
        DiffUtil.calculateDiff(MovieDiffUtilsCallback(newData, movies)).dispatchUpdatesTo(this)

        movies.clear()
        movies.addAll(newData)
    }

    class MovieViewHolder(private val itemBinding: RowMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: Movie) {
            itemBinding.preview.clipToOutline = true
            itemBinding.preview.background =
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_movie)

            itemBinding.name.text = movie.title
            itemBinding.adult.isVisible = movie.adult
            itemBinding.tags.text = movie.genreIds?.joinToString(", ")
            itemBinding.review.text = movie.voteCount.toString().plus(" Reviews")
            itemBinding.rating.rating = movie.voteAverage.toFloat() / 2

            val url = "https://image.tmdb.org/t/p/w780" + movie.posterPath

            itemBinding.preview.load(url) {
                crossfade(true)
                error(R.drawable.ic_launcher_foreground)
            }
        }
    }
}