package leo.com.pumpyourself.controllers.trainings

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.databinding.LayoutTrainingsBinding

class TrainingsController : BaseController<LayoutTrainingsBinding>() {

  override lateinit var binding: LayoutTrainingsBinding

  override fun initController() {
    val dataList = listOf (
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3"),
        Training("Make 15 squattings", "Squattings plan", "day 3")
    )

    binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, TrainingsAdapter(), dataList)
  }

  override fun getLayoutId(): Int = R.layout.layout_trainings

  override fun getTitle(): String = "Trainings"
}


