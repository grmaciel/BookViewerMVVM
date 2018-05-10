package com.gilson.bookviewer.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerDecoratorSpace : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, 10)
    }
}