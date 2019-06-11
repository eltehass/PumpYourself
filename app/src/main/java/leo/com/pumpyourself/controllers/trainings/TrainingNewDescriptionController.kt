package leo.com.pumpyourself.controllers.trainings

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.DayExercisesAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.trainings.extras.ItemTrainingNew
import leo.com.pumpyourself.databinding.LayoutTrainingNewDescriptionBinding

class TrainingNewDescriptionController : BaseController<LayoutTrainingNewDescriptionBinding>() {

    override lateinit var binding: LayoutTrainingNewDescriptionBinding

    override fun initController() {

        val itemTraining = arguments?.get("item_training_new") as ItemTrainingNew? ?: ItemTrainingNew("","")

        binding.tvTitle.text = itemTraining.title
        binding.tvDescription.text = itemTraining.description

        val dayExercises = listOf(
            ItemDayExercise("Day 1", "Run 1 km"),
            ItemDayExercise("Day 2", "Run 2 km"),
            ItemDayExercise("Day 3", "Run 3 km"),
            ItemDayExercise("Day 4", "Run 4 km")
        )

        binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), dayExercises)

        // TODO: Add the training starting
        binding.tvStart.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getLayoutId(): Int = R.layout.layout_training_new_description

    override fun getTitle(): String = "View training"

}