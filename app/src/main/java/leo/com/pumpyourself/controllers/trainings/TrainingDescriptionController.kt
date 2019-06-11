package leo.com.pumpyourself.controllers.trainings

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.*
import leo.com.pumpyourself.controllers.trainings.extras.ItemTraining
import leo.com.pumpyourself.databinding.LayoutTrainingDescriptionBinding

class TrainingDescriptionController : BaseController<LayoutTrainingDescriptionBinding>() {

    override lateinit var binding: LayoutTrainingDescriptionBinding

    override fun initController() {

        val itemTraining = arguments?.get("item_training") as ItemTraining? ?: ItemTraining("","", "")

        binding.tvTitle.text = itemTraining.title
        binding.tvDescription.text = itemTraining.description

        // TODO: Change the text color according to current day
        val dayExercises = listOf(
            ItemDayExercise("Day 1", "Run 1 km"),
            ItemDayExercise("Day 2", "Run 2 km"),
            ItemDayExercise("Day 3", "Run 3 km"),
            ItemDayExercise("Day 4", "Run 4 km")
        )

        binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), dayExercises)

        // TODO: Add the training stopping
        binding.tvStop.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getLayoutId(): Int = R.layout.layout_training_description

    override fun getTitle(): String = "View training"
}