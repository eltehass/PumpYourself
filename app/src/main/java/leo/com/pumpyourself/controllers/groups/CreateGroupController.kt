package leo.com.pumpyourself.controllers.groups

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.Constants.GALLERY_REQUEST_CODE
import leo.com.pumpyourself.common.ImageGalleryEvent
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.trainings.extras.DayExercisesAdapter
import leo.com.pumpyourself.controllers.trainings.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.trainings.extras.ItemMember
import leo.com.pumpyourself.controllers.trainings.extras.MembersAdapter
import leo.com.pumpyourself.databinding.LayoutCreateGroupBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CreateGroupController : BaseController<LayoutCreateGroupBinding>() {

  override lateinit var binding: LayoutCreateGroupBinding

  private lateinit var dialog: AlertDialog

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
        (binding.rvDays.adapter as DayExercisesAdapter).let{ adapter ->
          adapter.addData(ItemDayExercise("Day${adapter.itemCount+1}", dialogEditText.text.toString()))
        }

        dialogEditText.setText("")
        dialog.dismiss()
      }
    }

    dialog.setView(dialogView)
    dialog.setCancelable(true)

    return view
  }

  override fun initController() {

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
    binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), listOf())

    binding.tvAddMember.setOnClickListener {
      show(TAB_GROUPS, FriendsController())
    }

    binding.tvAddDay.setOnClickListener {
      dialog.show()
    }

    binding.ivGroupIcon.setOnClickListener {
      val intent = Intent(Intent.ACTION_PICK).apply {
          type = "image/*"
          putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
      }

      startActivityForResult(intent, GALLERY_REQUEST_CODE)
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

  override fun getLayoutId(): Int = R.layout.layout_create_group

  override fun getTitle(): String = "Create group"

}