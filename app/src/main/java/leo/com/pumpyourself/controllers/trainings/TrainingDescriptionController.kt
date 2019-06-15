package leo.com.pumpyourself.controllers.trainings

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.DayExercisesAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.trainings.extras.ItemTraining
import leo.com.pumpyourself.databinding.LayoutTrainingDescriptionBinding
import leo.com.pumpyourself.network.PumpYourSelfService
import leo.com.pumpyourself.network.StopTrainingRequest

class TrainingDescriptionController : BaseController<LayoutTrainingDescriptionBinding>() {

    override lateinit var binding: LayoutTrainingDescriptionBinding

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1
        val itemTraining = arguments?.get("item_training") as ItemTraining? ?:
            ItemTraining(0, "","", "", listOf())

        binding.tvTitle.text = itemTraining.title
        binding.tvDescription.text = itemTraining.description

        binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), itemTraining.days)

        binding.tvStop.setOnClickListener {
            asyncSafe {
                PumpYourSelfService.service.stopTraining(
                    StopTrainingRequest(userId, itemTraining.trainingId)).await()
                mainActivity.onBackPressed()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_training_description

    override fun getTitle(): String = "View training"
}