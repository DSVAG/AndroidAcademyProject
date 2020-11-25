package com.dsvag.androidacademyproject.data.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.data.models.Movie
import com.dsvag.androidacademyproject.databinding.RowMovieBinding

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
            itemBinding.name.text = movie.name
            itemBinding.length.text = movie.length.toString().plus(" minutes")
            itemBinding.ageLimit.text = movie.ageLimit.toString().plus("+")
            itemBinding.review.text = movie.reviews.toString().plus(" Reviews")
            itemBinding.tags.text = movie.tags.joinToString(", ")
            itemBinding.rating.rating = movie.rating.toFloat()

            Glide
                .with(itemBinding.root)
                .load(movie.preview)
                .optionalCenterInside()
                .into(itemBinding.preview)
        }
    }
}