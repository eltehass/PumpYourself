package com.wezom.kiviremote.presentation.base.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class LazyAdapter <DataType> (val lazyViewHolderClickListener: OnLazyViewHolderClickListener<DataType>? = null) : RecyclerView.Adapter<LazyViewHolder<DataType>>() {

    protected val data = mutableListOf<DataType>()

    fun swapData(newData: List<DataType>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: LazyViewHolder<DataType>, position: Int) {
        holder.bindData(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LazyViewHolder<DataType> {
        return createViewHolder(LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false))
    }

    override fun getItemCount(): Int = data.size

    abstract fun createViewHolder(view: View): LazyViewHolder<DataType>

    abstract fun getLayoutId(): Int

}