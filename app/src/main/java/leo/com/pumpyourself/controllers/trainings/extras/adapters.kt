package leo.com.pumpyourself.controllers.trainings.extras

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.ItemDayExerciseBinding
import leo.com.pumpyourself.databinding.ItemTrainingBinding
import leo.com.pumpyourself.databinding.ItemTrainingNewBinding

class TrainingsAdapter(onClick: OnItemClickListener<ItemTraining>)
  : LazyAdapter<ItemTraining, ItemTrainingBinding>(onClick) {

  override fun bindData(data: ItemTraining, binding: ItemTrainingBinding) {
    binding.tvTitle.text = data.title
    binding.tvDescription.text = data.description
    binding.tvDay.text = data.day
    binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
  }

  override fun getLayoutId(): Int = R.layout.item_training

}

class TrainingNewAdapter(onClick: OnItemClickListener<ItemTrainingNew>)
  : LazyAdapter<ItemTrainingNew, ItemTrainingNewBinding>(onClick) {

  override fun bindData(data: ItemTrainingNew, binding: ItemTrainingNewBinding) {
    binding.tvTitle.text = data.title
    binding.tvDescription.text = data.description
    binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
  }

  override fun getLayoutId(): Int = R.layout.item_training_new

}