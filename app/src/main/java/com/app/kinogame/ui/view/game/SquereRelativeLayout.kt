package com.app.kinogame.ui.view.game

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class SquereRelativeLayout : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val heightMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
