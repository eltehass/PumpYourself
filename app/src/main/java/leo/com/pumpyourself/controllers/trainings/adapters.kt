package leo.com.pumpyourself.controllers.trainings

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.TrainingItemBinding

class TrainingsAdapter : LazyAdapter<Training, TrainingItemBinding>() {

  override fun bindData(data: Training, binding: TrainingItemBinding) {
    binding.tvTitle.text = data.title
    binding.tvDescription.text = data.description
    binding.tvDay.text = data.day
  }

  override fun getLayoutId(): Int = R.layout.training_item

}