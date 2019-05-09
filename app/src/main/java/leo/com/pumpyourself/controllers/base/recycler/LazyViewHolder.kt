package com.wezom.kiviremote.presentation.base.recycler

import android.support.v7.widget.RecyclerView
import android.view.View

open class LazyViewHolder<DataType>(val view: View, val onLazyViewHolderClickListener: OnLazyViewHolderClickListener<DataType>? = null) : RecyclerView.ViewHolder(view) {
    open fun bindData(data: DataType) {
        onLazyViewHolderClickListener?.let { clickListener -> view.setOnClickListener { clickListener.onLazyItemClick(data) } }
    }
}