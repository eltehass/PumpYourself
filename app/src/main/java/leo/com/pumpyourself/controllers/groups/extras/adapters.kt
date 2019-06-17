package leo.com.pumpyourself.controllers.groups.extras

import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.setCircleImgResource
import leo.com.pumpyourself.common.setCircleImgUrl
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.ItemChooseFriendBinding
import leo.com.pumpyourself.databinding.ItemDayExerciseBinding
import leo.com.pumpyourself.databinding.ItemGroupBinding
import leo.com.pumpyourself.databinding.ItemMemberBinding

class GroupsAdapter(onClick: OnItemClickListener<ItemGroup>) : LazyAdapter<ItemGroup, ItemGroupBinding>(onClick) {

  override fun bindData(data: ItemGroup, binding: ItemGroupBinding) {
    binding.tvName.text = data.name
    binding.tvDescription.text = data.description
    binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }

    if (data.imgUrl.isNotEmpty()) {
      binding.ivIcon.setCircleImgUrl(data.imgUrl)
    } else {
      binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
    }
  }

  override fun getLayoutId(): Int = R.layout.item_group
}

class MembersAdapter : LazyAdapter<ItemMember, ItemMemberBinding>() {

  override fun bindData(data: ItemMember, binding: ItemMemberBinding) {
    binding.tvName.text = data.name

    if (data.imgUrl.isNotEmpty()) {
      binding.ivIcon.setCircleImgUrl(data.imgUrl)
    } else {
      binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
    }
  }

  override fun getLayoutId(): Int = R.layout.item_member
}

class DayExercisesAdapter : LazyAdapter<ItemDayExercise, ItemDayExerciseBinding>() {

  override fun bindData(data: ItemDayExercise, binding: ItemDayExerciseBinding) {
    binding.tvDayName.text = data.name
    binding.tvDayDescription.text = data.description
  }

  override fun getLayoutId(): Int = R.layout.item_day_exercise
}

class FriendsAdapter(onClick: OnItemClickListener<ItemFriend>) : LazyAdapter<ItemFriend, ItemChooseFriendBinding>(onClick) {

  override fun bindData(data: ItemFriend, binding: ItemChooseFriendBinding) {
    binding.tvName.text = data.name
    binding.tvStatus.text = data.status
    binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }

    if (data.imgUrl.isNotEmpty()) {
      binding.ivIcon.setCircleImgUrl(data.imgUrl)
    } else {
      binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
    }
  }

  override fun getLayoutId(): Int = R.layout.item_choose_friend
}