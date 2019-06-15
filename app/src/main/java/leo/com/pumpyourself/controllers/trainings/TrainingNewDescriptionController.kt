package leo.com.pumpyourself.controllers.trainings

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.DayExercisesAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.trainings.extras.ItemTraining
import leo.com.pumpyourself.databinding.LayoutTrainingNewDescriptionBinding
import leo.com.pumpyourself.network.PumpYourSelfService
import leo.com.pumpyourself.network.StartTrainingRequest
import java.text.SimpleDateFormat
import java.util.*

class TrainingNewDescriptionController : BaseController<LayoutTrainingNewDescriptionBinding>() {

    override lateinit var binding: LayoutTrainingNewDescriptionBinding

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1
        val itemTraining = arguments?.get("item_training") as ItemTraining? ?:
            ItemTraining(0, "","", "", listOf())

        binding.tvTitle.text = itemTraining.title
        binding.tvDescription.text = itemTraining.description

        binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), itemTraining.days)

        binding.tvStart.setOnClickListener {

            val currDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .format(Calendar.getInstance().time)

            asyncSafe {
                PumpYourSelfService.service.startTraining(
                    StartTrainingRequest(userId, itemTraining.trainingId, currDateStr)).await()

                mainActivity.onBackPressed()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_training_new_description

    override fun getTitle(): String = "View training"

}