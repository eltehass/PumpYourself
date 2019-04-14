package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.view.View
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController

class TrainingsController : BaseController() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            show(TAB_TRAININGS, TrainingsDeep1Controller())
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_trainings

    override fun getTitle(): String = "Trainings"

}