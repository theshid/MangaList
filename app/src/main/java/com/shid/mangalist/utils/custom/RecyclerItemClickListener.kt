package com.shid.mangalist.utils.custom

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context: Context?, listener: OnRecyclerViewItemClickListener?)
    : RecyclerView.OnItemTouchListener {

    var mGestureDetector: GestureDetector? = null
    var recyclerViewItemClickListener: OnRecyclerViewItemClickListener? = null

    init {
        recyclerViewItemClickListener = listener
        mGestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })
    }


    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView: View? = view.findChildViewUnder(e.x, e.y)
        if (childView != null && recyclerViewItemClickListener != null && mGestureDetector!!.onTouchEvent(
                e
            )
        ) {
            recyclerViewItemClickListener!!.onItemClick(
                view,
                childView,
                view.getChildLayoutPosition(childView)
            )
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }


    override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}

    interface OnRecyclerViewItemClickListener {
        fun onItemClick(parentView: View?, childView: View?, position: Int)
    }
}