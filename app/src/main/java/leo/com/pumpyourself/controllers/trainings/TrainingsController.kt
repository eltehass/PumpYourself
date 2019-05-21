package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.trainings.extras.ItemTraining
import leo.com.pumpyourself.controllers.trainings.extras.TrainingsAdapter
import leo.com.pumpyourself.databinding.LayoutTrainingsBinding

class TrainingsController : BaseController<LayoutTrainingsBinding>(), LazyAdapter.OnItemClickListener<ItemTraining> {

  override lateinit var binding: LayoutTrainingsBinding

  override fun initController() {
    val dataList = listOf (
        ItemTraining("Make 15 squattings", "Squattings plan", "day 3"),
        ItemTraining("Run 3 km", "Morning run", "day 4")
    )

    binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, TrainingsAdapter(this), dataList)
    binding.fabAction.setOnClickListener { show(TAB_TRAININGS, TrainingNewController()) }
  }

  override fun onLazyItemClick(data: ItemTraining) {
    val editController = TrainingDescriptionController()
    val bundle = Bundle()
    bundle.putSerializable("item_training", data)
    editController.arguments = bundle

    show(TAB_TRAININGS, editController)
  }

  override fun getLayoutId(): Int = R.layout.layout_trainings

  override fun getTitle(): String = "Trainings"
}


