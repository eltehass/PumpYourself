package leo.com.pumpyourself.controllers.trainings.extras

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.ItemTrainingBinding

class TrainingsAdapter : LazyAdapter<ItemTraining, ItemTrainingBinding>() {

  override fun bindData(data: ItemTraining, binding: ItemTrainingBinding) {
    binding.tvTitle.text = data.title
    binding.tvDescription.text = data.description
    binding.tvDay.text = data.day
  }

  override fun getLayoutId(): Int = R.layout.item_training

}