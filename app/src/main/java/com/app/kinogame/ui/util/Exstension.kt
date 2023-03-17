package com.app.kinogame.ui.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View

fun Context.getResourceColor(resourceId: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(resourceId)
    } else {
        resources.getColor(resourceId)
    }
}

fun Context.getResourceDrawable(resourceId: Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getDrawable(resourceId)
    } else {
        resources.getDrawable(resourceId)
    }
}

fun View.setVisibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setVisibleOrInvisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}