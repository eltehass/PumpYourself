package com.wezom.kiviremote.presentation.base.recycler

interface OnLazyViewHolderClickListener <DataType> {
    fun onLazyItemClick(data: DataType)
}