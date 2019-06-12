package leo.com.pumpyourself.controllers.base.recycler

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class LazyAdapter <DataType, LayoutClassBinding : ViewDataBinding> (val itemClickListener: OnItemClickListener<DataType>? = null) : RecyclerView.Adapter<LazyAdapter.NewLazyViewHolder<DataType>>() {

    private val data = mutableListOf<DataType>()

    fun swapData(newData: List<DataType>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(dataElement: DataType) {
        data.add(dataElement)
        notifyItemInserted(data.size - 1)
    }

    fun removeData(dataElement: DataType) {
        data.remove(dataElement)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewLazyViewHolder<DataType>, position: Int) {
        holder.bindData(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewLazyViewHolder<DataType> {
        val binding: LayoutClassBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(), parent, false)
        return object: NewLazyViewHolder<DataType>(binding) {
            override fun bindData(data: DataType) {
                bindData(data, binding)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    abstract fun bindData(data: DataType, binding: LayoutClassBinding)

    abstract fun getLayoutId(): Int

    abstract class NewLazyViewHolder<DataType>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bindData(data: DataType)
    }

    interface OnItemClickListener <DataType> {
        fun onLazyItemClick(data: DataType)
    }

}