package com.dsvag.androidacademyproject.ui.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.RowMovieSmallBinding
import com.dsvag.androidacademyproject.models.moviecredits.Cast

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val castList: MutableList<Cast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(RowMovieSmallBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int = castList.size

    fun setData(newCastList: List<Cast>) {
        castList.apply {
            castList.clear()
            addAll(newCastList)
        }

        notifyDataSetChanged()
    }

    class MovieViewHolder(private val itemBinding: RowMovieSmallBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(cast: Cast) {
            itemBinding.poster.clipToOutline = true

            val url = "https://image.tmdb.org/t/p/w780" + cast.posterPath

            itemBinding.poster.load(url) {
                crossfade(true)
                error(R.drawable.ic_launcher_foreground)
            }

            itemBinding.name.text = cast.originalTitle

            itemBinding.root.setOnClickListener {
                val bundle = Bundle().apply { putInt("movieId", cast.id) }
                itemBinding.root.findNavController()
                    .navigate(R.id.action_creditFragment_to_movieDetailsFragment, bundle)
            }
        }
    }
}
