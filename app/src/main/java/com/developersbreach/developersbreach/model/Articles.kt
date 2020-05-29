package com.developersbreach.developersbreach.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Articles(
    val id: Int,
    val title: String
) : Parcelable {

    companion object DiffCallback : DiffUtil.ItemCallback<Articles>() {

        override fun areItemsTheSame(
            oldItem: Articles,
            newItem: Articles
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Articles,
            newItem: Articles
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
