package leo.com.pumpyourself.controllers.trainings

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutTrainingsDeep1Binding

class TrainingsDeep1Controller : BaseController<LayoutTrainingsDeep1Binding>() {

  override lateinit var binding: LayoutTrainingsDeep1Binding

  override fun getLayoutId(): Int = R.layout.layout_trainings_deep1

  override fun getTitle(): String = "TrainingsDeep1"

}