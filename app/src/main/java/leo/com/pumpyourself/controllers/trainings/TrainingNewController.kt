package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.trainings.extras.ItemTraining
import leo.com.pumpyourself.controllers.trainings.extras.TrainingNewAdapter
import leo.com.pumpyourself.databinding.LayoutTrainingNewBinding
import leo.com.pumpyourself.network.PumpYourSelfService
import java.text.SimpleDateFormat
import java.util.*

class TrainingNewController : BaseController<LayoutTrainingNewBinding>(), LazyAdapter.OnItemClickListener<ItemTraining> {

    override lateinit var binding: LayoutTrainingNewBinding

    var userId = 1

    override fun initController() {

        val controllerThis = this
        userId = arguments?.get("user_id") as Int? ?: 1

        asyncSafe {
            val networkResult = PumpYourSelfService.service.getAllPublicTrainings().await()

            val trainings = networkResult.groupBy { it.trainingID }

            val dataList = trainings.map {
                ItemTraining(it.key, it.value[0].trainingName, it.value[0].trainingDescription,"",
                    it.value.map { itIn -> ItemDayExercise("Day " + itIn.dayNumber, itIn.dayPlan) }
                        .sortedBy { itIn -> itIn.name }
                )
            }

            binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, TrainingNewAdapter(controllerThis), dataList)
            binding.cvNewCustom.setOnClickListener {
                val trainingNewCustomController = TrainingNewCustomController()
                val bundle = Bundle()
                bundle.putSerializable("user_id", userId)
                trainingNewCustomController.arguments = bundle
                show(TAB_TRAININGS, trainingNewCustomController)
            }
        }
    }

    override fun onLazyItemClick(data: ItemTraining) {
        val viewNewTrainingController = TrainingNewDescriptionController()
        val bundle = Bundle()
        bundle.putSerializable("user_id", userId)
        bundle.putSerializable("item_training", data)
        viewNewTrainingController.arguments = bundle

        show(TAB_TRAININGS, viewNewTrainingController)
    }

    override fun getLayoutId(): Int = R.layout.layout_training_new

    override fun getTitle(): String = "Add training"
}