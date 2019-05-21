package leo.com.pumpyourself.controllers.trainings

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.trainings.extras.ItemTrainingNew
import leo.com.pumpyourself.controllers.trainings.extras.TrainingNewAdapter
import leo.com.pumpyourself.databinding.LayoutTrainingNewBinding

class TrainingNewController : BaseController<LayoutTrainingNewBinding>(), LazyAdapter.OnItemClickListener<ItemTrainingNew> {

    override lateinit var binding: LayoutTrainingNewBinding

    override fun initController() {
        val dataList = listOf (
            ItemTrainingNew("Morning run", "Helps to keep fit"),
            ItemTrainingNew("Morning run", "Helps to keep fit"),
            ItemTrainingNew("Morning run", "Helps to keep fit")
        )

        binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, TrainingNewAdapter(this), dataList)
        binding.cvNewCustom.setOnClickListener { show(TAB_TRAININGS, TrainingNewCustomController()) }
    }

    override fun onLazyItemClick(data: ItemTrainingNew) {
        val viewNewTrainingController = TrainingNewDescriptionController()
        val bundle = Bundle()
        bundle.putSerializable("item_training_new", data)
        viewNewTrainingController.arguments = bundle

        show(TAB_TRAININGS, viewNewTrainingController)
    }

    override fun getLayoutId(): Int = R.layout.layout_training_new

    override fun getTitle(): String = "Add training"
}