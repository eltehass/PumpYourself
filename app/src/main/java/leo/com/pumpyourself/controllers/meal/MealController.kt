package leo.com.pumpyourself.controllers.meal

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.databinding.ItemExampleBinding
import leo.com.pumpyourself.databinding.LayoutMealBinding

class MealController : BaseController<LayoutMealBinding>() {

  override lateinit var binding: LayoutMealBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setOnClickListener { show(TAB_MEAL, MealDeep1Controller()) }

    binding.textView.text = "Hellout world"

    val dataList = listOf(
      DataText("title1","content1"),
      DataText("title2","content2"),
      DataText("title3","content3"),
      DataText("title4","content4"))

    binding.rvContainer.initWithLinLay(LinearLayoutManager.VERTICAL, CustomAdapter(), dataList)
  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}

data class DataText(val title: String, val content: String)

class CustomAdapter : LazyAdapter<DataText, ItemExampleBinding>() {

  override fun bindData(data: DataText, binding: ItemExampleBinding) {
    binding.tvTitle.text = data.title
    binding.tvContent.text = data.content
  }

  override fun getLayoutId(): Int = R.layout.item_example

}