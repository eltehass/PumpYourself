package leo.com.pumpyourself.controllers.base.recycler

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.wezom.kiviremote.presentation.base.recycler.LazyAdapter

fun <DataType> RecyclerView.initWithLinLay(context: Context, orientation: Int, adapter: LazyAdapter<DataType>, data: List<DataType>) {
    this.apply {
        this.adapter = adapter
        this.layoutManager = LinearLayoutManager(context, orientation, false)
        setHasFixedSize(true)
    }

    adapter.swapData(data)
}

fun <DataType> RecyclerView.initWithGridLay(context: Context, spanCount: Int, adapter: LazyAdapter<DataType>, data: List<DataType>) {
    this.apply {
        this.adapter = adapter
        this.layoutManager = GridLayoutManager(context, spanCount)
        setHasFixedSize(true)
    }

    adapter.swapData(data)
}