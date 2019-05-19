package leo.com.pumpyourself.controllers.groups

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.setCircleImgResource
import leo.com.pumpyourself.common.setCircleImgUrl
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.trainings.extras.*
import leo.com.pumpyourself.databinding.LayoutEditGroupBinding

class EditGroupController : BaseController<LayoutEditGroupBinding>() {

  override lateinit var binding: LayoutEditGroupBinding

  override fun initController() {

    val itemGroup = arguments?.get("item_group") as ItemGroup? ?: ItemGroup("","", "")

    binding.tvGroupName.text = itemGroup.name
    binding.tvGroupDescription.text = itemGroup.description

    if (itemGroup.imgUrl.isNotEmpty()) {
      binding.ivGroupIcon.setCircleImgUrl(itemGroup.imgUrl)
    } else {
      binding.ivGroupIcon.setCircleImgResource(R.drawable.ic_launcher_background)
    }

    val members = listOf(
            ItemMember("Leo", "http://upe.pl.ua:8080/images/users?image_id=1"),
            ItemMember("Leo", "http://upe.pl.ua:8080/images/users?image_id=1"),
            ItemMember("Leo", "http://upe.pl.ua:8080/images/users?image_id=1"),
            ItemMember("Leo", ""),
            ItemMember("Leo", ""),
            ItemMember("Leo", ""),
            ItemMember("Leo", ""),
            ItemMember("Leo", "")
    )

    val dayExercises = listOf(
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km"),
            ItemDayExercise("Day1", "Run 1 km")
    )

    binding.rvMembers.initWithLinLay(LinearLayout.VERTICAL, MembersAdapter(), members)
    binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), dayExercises)

  }

  override fun getLayoutId(): Int = R.layout.layout_edit_group

  override fun getTitle(): String = "Edit group"

}