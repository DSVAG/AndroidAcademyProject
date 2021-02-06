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
import com.dsvag.androidacademyproject.models.movies.Result
import com.dsvag.androidacademyproject.ui.movies.utils.MovieDiffUtilsCallback

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val resultList: MutableList<Result> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowMovieBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener {

        }

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(resultList[position])

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply { putInt("movieId", resultList[position].id) }
            holder.itemView.findNavController()
                .navigate(R.id.action_moviesFragment_to_movieDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = resultList.size

    fun setData(newData: List<Result>) {
        DiffUtil.calculateDiff(MovieDiffUtilsCallback(newData, resultList)).dispatchUpdatesTo(this)

        resultList.clear()
        resultList.addAll(newData)
    }

    class MovieViewHolder(private val itemBinding: RowMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(result: Result) {
            itemBinding.preview.clipToOutline = true
            itemBinding.preview.background =
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_movie)

            itemBinding.name.text = result.title
            itemBinding.adult.isVisible = result.adult
            itemBinding.tags.text = result.genreIds.joinToString(", ")
            itemBinding.review.text = result.voteCount.toString().plus(" Reviews")
            itemBinding.rating.rating = result.voteAverage.toFloat() / 2

            val url = "https://image.tmdb.org/t/p/w780" + result.posterPath

            itemBinding.preview.load(url) {
                crossfade(true)
                error(R.drawable.ic_launcher_foreground)
            }
        }
    }
}