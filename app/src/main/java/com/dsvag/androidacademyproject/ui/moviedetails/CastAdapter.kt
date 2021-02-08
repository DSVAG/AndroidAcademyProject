package com.dsvag.androidacademyproject.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.RowActorBinding
import com.dsvag.androidacademyproject.models.credits.Cast

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private val castList: MutableList<Cast> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CastViewHolder(RowActorBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(castList[position])

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply { putLong("castId", castList[position].id) }
            holder.itemView.findNavController()
                .navigate(R.id.action_movieDetailsFragment_to_creditFragment, bundle)
        }
    }

    override fun getItemCount(): Int = castList.size

    fun setData(newCastList: List<Cast>) {
        castList.apply {
            clear()
            addAll(newCastList)
        }

        notifyDataSetChanged()
    }

    class CastViewHolder(private val itemBinding: RowActorBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(cast: Cast) {
            itemBinding.photo.clipToOutline = true
            itemBinding.photo.background =
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_actor_photo)

            itemBinding.name.text = cast.originalName
            itemBinding.character.text = cast.character

            val url = "https://image.tmdb.org/t/p/h632" + cast.profilePath

            itemBinding.photo.load(url) {
                crossfade(true)
                error(R.drawable.ic_launcher_foreground)
            }
        }
    }
}