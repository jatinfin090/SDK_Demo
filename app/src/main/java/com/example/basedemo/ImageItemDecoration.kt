package com.example.basedemo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ImageItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION) {
            outRect.left = space / 2
            outRect.right = space / 2
        }
    }
}