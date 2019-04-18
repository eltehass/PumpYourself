package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.view.View
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController
import leo.com.pumpyourself.databinding.LayoutTrainingsBinding

class  TrainingsController : BaseController<LayoutTrainingsBinding>() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setOnClickListener { show(TAB_TRAININGS, TrainingsDeep1Controller()) }
  }

  override fun getLayoutId(): Int = R.layout.layout_trainings

  override fun getTitle(): String = "Trainings"

  override fun setDataForView(binding: LayoutTrainingsBinding) { binding.controller = this }

}