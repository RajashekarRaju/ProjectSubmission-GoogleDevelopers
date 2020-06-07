package com.developersbreach.developersbreach.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.R

class RecyclerViewItemDecoration(private val itemOffset: Int) : RecyclerView.ItemDecoration() {


    companion object {

        fun setItemSpacing(
            resources: Resources,
            recyclerView: RecyclerView
        ) {
            val spacingInPixels =
                resources.getDimensionPixelSize(R.dimen.recycler_view_spacing_dimen)
            recyclerView.addItemDecoration(RecyclerViewItemDecoration(spacingInPixels))
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset)
    }
}
