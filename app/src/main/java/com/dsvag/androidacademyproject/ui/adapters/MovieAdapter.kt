package com.dsvag.androidacademyproject.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.RowMovieBinding
import com.dsvag.androidacademyproject.models.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movieList: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(RowMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply { putParcelable("movie", movieList[position]) }
            holder.itemView.findNavController()
                .navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = movieList.size

    fun setData(newData: List<Movie>) {
        movieList.clear()
        movieList.addAll(newData)
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        private val itemBinding: RowMovieBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: Movie) {
            itemBinding.preview.clipToOutline = true
            itemBinding.preview.background =
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_movie)

            itemBinding.name.text = movie.title
            itemBinding.length.text = movie.runtime.toString().plus(" minutes")
            itemBinding.ageLimit.text = movie.minimumAge.toString().plus("+")
            itemBinding.tags.text = movie.genres.joinToString(", ") { it.name }
            itemBinding.review.text = movie.numberOfRatings.toString().plus(" Reviews")
            itemBinding.rating.rating = movie.ratings / 2

            Glide
                .with(itemBinding.root)
                .load(movie.poster)
                .optionalFitCenter()
                .into(itemBinding.preview)
        }
    }
}