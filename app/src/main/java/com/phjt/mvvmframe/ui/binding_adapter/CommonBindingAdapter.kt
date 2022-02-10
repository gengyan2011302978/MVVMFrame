package com.phjt.mvvmframe.ui.binding_adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.phjt.mvvmframe.common.AppImageLoader


@BindingAdapter("imageUrl", "placeHolder", "errorImage", requireAll = false)
fun imageUrl(imageView: ImageView, url: String, placeHolder: Int, errorImage: Int) {
    AppImageLoader.loadUrl(url, imageView, placeHolder, errorImage)
}


@BindingAdapter("visible")
fun visible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("textColor")
fun setTextColor(textView: TextView, textColor: Int) {
    textView.setTextColor(textView.context.getColor(textColor))
}

@BindingAdapter("selected")
fun setSelected(view: View, select: Boolean) {
    view.isSelected = select
}