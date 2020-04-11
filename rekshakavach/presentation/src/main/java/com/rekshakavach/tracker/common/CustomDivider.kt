package com.rekshakavach.tracker.common

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
const val VIEW_NORMAL = 1
class CustomDivider(private val mDivider: Drawable?) : ItemDecoration() {

    private var leftPadding = 0
    private var rightPadding = 0
    var enableCustomPadding = false
    fun padding(left: Int, right: Int): CustomDivider {
        enableCustomPadding = true
        leftPadding = left
        rightPadding = right
        return this
    }

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        var dividerLeft = parent.paddingLeft
        var dividerRight = parent.width - parent.paddingRight
        if (enableCustomPadding) {
            dividerLeft = leftPadding
            dividerRight = parent.width - rightPadding
        }
        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)
            var tag = child.tag as Int
            if(tag == VIEW_NORMAL) {
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + mDivider!!.intrinsicHeight
                mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                mDivider.draw(canvas)
            }
        }
    }

}