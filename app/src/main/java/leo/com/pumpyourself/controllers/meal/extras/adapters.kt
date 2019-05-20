package leo.com.pumpyourself.controllers.meal.extras

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.ItemMealUnitBinding

class MealUnitAdapter : LazyAdapter<ItemMealUnit, ItemMealUnitBinding>() {

    override fun bindData(data: ItemMealUnit, binding: ItemMealUnitBinding) {
        binding.tvName.text = data.name
        binding.tvWeight.text = data.value
    }

    override fun getLayoutId(): Int = R.layout.item_meal_unit

}