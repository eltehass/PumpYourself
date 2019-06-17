package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.AccountManager
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.trainings.extras.ItemTraining
import leo.com.pumpyourself.controllers.trainings.extras.TrainingsAdapter
import leo.com.pumpyourself.databinding.LayoutTrainingsBinding
import leo.com.pumpyourself.network.PumpYourSelfService
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TrainingsController : BaseController<LayoutTrainingsBinding>(), LazyAdapter.OnItemClickListener<ItemTraining> {

    override lateinit var binding: LayoutTrainingsBinding

    var userId = AccountManager.getId(context!!)

    override fun initController() {

        val controllerThis = this

        val currDate = Calendar.getInstance()

        asyncSafe {

            val networkResult = PumpYourSelfService.service.getAllUserTrainings(userId).await()

            val trainings = networkResult.groupBy { it.trainingID }

            val dataList = trainings.map {
                ItemTraining(it.key, it.value[0].trainingName, it.value[0].trainingDescription,
                    "day " + daysBetween(
                        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(it.value[0].startDate),
                        currDate.time),
                    it.value.map { itIn -> ItemDayExercise("Day " + itIn.dayNumber, itIn.dayPlan) }
                        .sortedBy { itIn -> itIn.name }
                )
            }

            binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, TrainingsAdapter(controllerThis), dataList)

            binding.fabAction.setOnClickListener {
                val trainingNewController = TrainingNewController()
                val bundle = Bundle()
                bundle.putSerializable("user_id", userId)
                trainingNewController.arguments = bundle
                show(TAB_TRAININGS, trainingNewController)
            }
        }
    }

    override fun onLazyItemClick(data: ItemTraining) {
        val editController = TrainingDescriptionController()
        val bundle = Bundle()
        bundle.putSerializable("user_id", userId)
        bundle.putSerializable("item_training", data)
        editController.arguments = bundle

        show(TAB_TRAININGS, editController)
    }

    fun daysBetween(startDate: Date, endDate: Date): Int {
        val diff = endDate.time - startDate.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }

    override fun getLayoutId(): Int = R.layout.layout_trainings

    override fun getTitle(): String = "Trainings"
}
