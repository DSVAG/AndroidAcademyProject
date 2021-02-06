package com.dsvag.androidacademyproject.ui.movies.utils

import androidx.recyclerview.widget.DiffUtil
import com.dsvag.androidacademyproject.models.movies.Result

class MovieDiffUtilsCallback(
    private val newList: List<Result>,
    private val oldList: List<Result>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}