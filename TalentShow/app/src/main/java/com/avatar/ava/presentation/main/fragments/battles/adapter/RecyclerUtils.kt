package com.avatar.ava.presentation.main.fragments.battles.adapter

import android.graphics.Rect
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setMiddleDivider(
        itemCount: Int,
        divider: Int,
        showLastDivider: Boolean = true
) {
    val decorator = object : DividerItemDecoration(context, LinearLayoutManager.VERTICAL) {
        override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (showLastDivider) {
                super.getItemOffsets(outRect, view, parent, state)
            } else {
                if (position == itemCount - 1) {
                    outRect.setEmpty()
                } else {
                    super.getItemOffsets(outRect, view, parent, state)
                }
            }
        }
    }

    ResourcesCompat.getDrawable(resources, divider, null)?.let {
        decorator.setDrawable(it)
    }

    addItemDecoration(decorator)
}