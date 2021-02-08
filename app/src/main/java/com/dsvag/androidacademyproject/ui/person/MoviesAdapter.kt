package com.dsvag.androidacademyproject.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.RowMovieSmallBinding
import com.dsvag.androidacademyproject.models.movies.Movie

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(RowMovieSmallBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply { putInt("movieId", movies[position].id) }
            holder.itemView.findNavController()
                .navigate(R.id.action_personFragment_to_movieDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun setData(newMovies: List<Movie>) {
        movies.apply {
            movies.clear()
            addAll(newMovies)
        }

        notifyDataSetChanged()
    }

    class MovieViewHolder(private val itemBinding: RowMovieSmallBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie) {
            itemBinding.poster.clipToOutline = true

            val url = "https://image.tmdb.org/t/p/w780" + movie.posterPath

            itemBinding.poster.load(url) {
                crossfade(true)
                error(R.drawable.ic_launcher_foreground)
            }

            itemBinding.name.text = movie.title
        }
    }
}
