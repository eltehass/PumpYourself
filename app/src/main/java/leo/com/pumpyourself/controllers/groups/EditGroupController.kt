package leo.com.pumpyourself.controllers.groups

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.Constants
import leo.com.pumpyourself.common.ImageGalleryEvent
import leo.com.pumpyourself.common.setCircleImgResource
import leo.com.pumpyourself.common.setCircleImgUrl
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.*
import leo.com.pumpyourself.databinding.LayoutEditGroupBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class EditGroupController : BaseController<LayoutEditGroupBinding>() {

  override lateinit var binding: LayoutEditGroupBinding

  override fun initController() {

    val itemGroup = arguments?.get("item_group") as ItemGroup? ?: ItemGroup("", "", "")

    binding.etGroupName.setText(itemGroup.name)
    binding.etGroupDescription.setText(itemGroup.description)

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

    binding.tvAddMember.setOnClickListener {
      show(TAB_GROUPS, FriendsController())
    }

    binding.ivGroupIcon.setOnClickListener {
      val intent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
      }

      startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (!EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().register(this)
    }
  }

  @Subscribe
  fun onImageGalleryEvent(event: ImageGalleryEvent) {
    binding.ivGroupIcon.setImageURI(event.uri)
  }

  override fun getLayoutId(): Int = R.layout.layout_edit_group

  override fun getTitle(): String = "Edit group"
}