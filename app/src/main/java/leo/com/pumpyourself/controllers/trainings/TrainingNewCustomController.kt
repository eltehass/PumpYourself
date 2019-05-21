package leo.com.pumpyourself.controllers.trainings

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutTrainingNewCustomBinding

class TrainingNewCustomController : BaseController<LayoutTrainingNewCustomBinding>() {

    override lateinit var binding: LayoutTrainingNewCustomBinding

    override fun initController() {
        binding.tvSave.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getLayoutId(): Int = R.layout.layout_training_new_custom

    override fun getTitle(): String = "Custom training"

}