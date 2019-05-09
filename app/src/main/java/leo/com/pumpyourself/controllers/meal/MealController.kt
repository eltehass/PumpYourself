package leo.com.pumpyourself.controllers.meal

import android.os.Bundle
import android.view.View
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutMealBinding

class MealController : BaseController<LayoutMealBinding>() {

  override lateinit var binding: LayoutMealBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setOnClickListener { show(TAB_MEAL, MealDeep1Controller()) }

    binding.textView.text = "Hellout world"
    //binding.rvContainer.initWithLinLay(view.context, LinearLayoutManager.VERTICAL, CustomAdapter(), listData)
  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}

///** DATA CLASS THAT REPRESENT ITEM */
//data class DataText(val title: String, val content: String)
//
///** DATA HOLDER THAT GETTING AND BINDING DATA */
//class CustomViewHolder(view: View) : LazyViewHolder<DataText>(view) {
//
//  private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
//  private val tvContent = view.findViewById<TextView>(R.id.tv_content)
//
//  override fun bindData(data: DataText) {
//    super.bindData(data)
//    tvTitle.text = data.title
//    tvContent.text = data.content
//  }
//
//}
//
///** ADAPTER FOR CONVERTING DATA WITH TYPE "DataText" TO THE VIEW, RecyclerView using adapter for setting elements **/
//class CustomAdapter : LazyAdapter<DataText>() {
//
//  override fun createViewHolder(view: View): LazyViewHolder<DataText> = CustomViewHolder(view)
//
//  override fun getLayoutId(): Int = R.layout.item_example
//
//}