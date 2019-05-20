package leo.com.pumpyourself.controllers.trainings

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutTrainingsHistoryBinding

class TrainingsHistoryController : BaseController<LayoutTrainingsHistoryBinding>() {

    override lateinit var binding: LayoutTrainingsHistoryBinding

    override fun getLayoutId(): Int = R.layout.layout_trainings_history

    override fun getTitle(): String = "Trainings history"
}