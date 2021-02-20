package com.shid.mangalist.utils.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class CustomCardView extends MaterialCardView {

    private float ratio = 1.4f;

    public CustomCardView(Context context) {
        this(context, null);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio > 0) {
            int ratioHeight = (int) (getMeasuredWidth() * ratio);
            setMeasuredDimension(getMeasuredWidth(), ratioHeight);
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = ratioHeight;
            setLayoutParams(lp);
        }
    }
}