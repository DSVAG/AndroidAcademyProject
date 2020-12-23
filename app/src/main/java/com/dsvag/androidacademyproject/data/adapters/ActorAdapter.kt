package com.dsvag.androidacademyproject.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.data.models.Actor
import com.dsvag.androidacademyproject.databinding.RowActorBinding

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private val actorList: MutableList<Actor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ActorViewHolder(RowActorBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actorList[position])
    }

    override fun getItemCount() = actorList.size

    fun setData(newData: List<Actor>) {
        actorList.clear()
        actorList.addAll(newData)
        notifyDataSetChanged()
    }

    class ActorViewHolder(
        private val itemBinding: RowActorBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(actor: Actor) {
            itemBinding.name.text = actor.name
            itemBinding.photo.clipToOutline = true

            Glide
                .with(itemBinding.root)
                .load(actor.picture)
                .optionalFitCenter()
                .placeholder(R.drawable.bg_actor_photo)
                .into(itemBinding.photo)
        }
    }
}