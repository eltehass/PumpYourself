package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.DayExercisesAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.databinding.LayoutTrainingNewCustomBinding
import leo.com.pumpyourself.network.CreateTrainingRequest
import leo.com.pumpyourself.network.PumpYourSelfService
import leo.com.pumpyourself.network.StartTrainingRequest
import java.text.SimpleDateFormat
import java.util.*

class TrainingNewCustomController : BaseController<LayoutTrainingNewCustomBinding>() {

    override lateinit var binding: LayoutTrainingNewCustomBinding

    private lateinit var dialog: AlertDialog

    private val daysInfo: MutableList<ItemDayExercise> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        dialog = AlertDialog.Builder(binding.root.context).create()

        val dialogView = inflater.inflate(R.layout.dialog_add_day, null)
        val dialogEditText = dialogView.findViewById<EditText>(R.id.et_description)

        dialogView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { dialog.dismiss() }
        dialogView.findViewById<TextView>(R.id.tv_add).setOnClickListener {
            if (dialogEditText.text.toString().isEmpty()) {
                Toast.makeText(it.context, "Field is empty", Toast.LENGTH_LONG).show()
            } else {
                val adapter = binding.rvDays.adapter as DayExercisesAdapter
                adapter.addData(ItemDayExercise("Day ${adapter.itemCount + 1}", dialogEditText.text.toString()))

                daysInfo.add(ItemDayExercise("Day ${adapter.itemCount}", dialogEditText.text.toString()))

                dialogEditText.setText("")
                dialog.dismiss()
            }
        }

        dialog.setView(dialogView)
        dialog.setCancelable(true)

        return view
    }

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1

        binding.rvDays.initWithLinLay(LinearLayoutManager.VERTICAL, DayExercisesAdapter(), listOf())

        binding.tvAddDay.setOnClickListener {
            dialog.show()
        }

        binding.tvSave.setOnClickListener {

            val currDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .format(Calendar.getInstance().time)

            asyncSafe {
                val trainingId = PumpYourSelfService.service.createTraining(daysInfo.map {
                    CreateTrainingRequest(binding.tvTrainingName.text.toString(),
                        binding.tvTrainingDescription.text.toString(),
                        it.name.substring(4).toInt(),
                        it.description)
                }).await()

                PumpYourSelfService.service.startTraining(
                    StartTrainingRequest(userId, trainingId, currDateStr)).await()

                mainActivity.onBackPressed()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_training_new_custom

    override fun getTitle(): String = "Custom training"
}