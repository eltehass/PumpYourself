package leo.com.pumpyourself.controllers.profile.extras

import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.setCircleImgResource
import leo.com.pumpyourself.common.setCircleImgUrl
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.ItemAddFriendBinding
import leo.com.pumpyourself.databinding.ItemFriendNotificationBinding
import leo.com.pumpyourself.databinding.ItemGroupNotificationBinding
import leo.com.pumpyourself.databinding.ItemProfileFriendsBinding

class FriendsAdapter(onClick: OnItemClickListener<ItemFriend>)
    : LazyAdapter<ItemFriend, ItemProfileFriendsBinding>(onClick) {

    override fun bindData(data: ItemFriend, binding: ItemProfileFriendsBinding) {
        binding.tvName.text = data.name
        binding.tvStatus.text = data.status

        if (data.imgUrl.isNotEmpty()) {
            binding.ivIcon.setCircleImgUrl(data.imgUrl)
        } else {
            binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
        }

        binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
    }

    override fun getLayoutId(): Int = R.layout.item_profile_friends
}

class GroupsRequestsAdapter(onClick: OnItemClickListener<ItemGroup>)
    : LazyAdapter<ItemGroup, ItemGroupNotificationBinding>(onClick) {

    override fun bindData(data: ItemGroup, binding: ItemGroupNotificationBinding) {
        binding.tvName.text = data.name
        binding.tvDescription.text = data.description

        if (data.imgUrl.isNotEmpty()) {
            binding.ivIcon.setCircleImgUrl(data.imgUrl)
        } else {
            binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
        }

        binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
    }

    override fun getLayoutId(): Int = R.layout.item_group_notification
}

class FriendsRequestsAdapter(onClick: OnItemClickListener<ItemFriend>)
    : LazyAdapter<ItemFriend, ItemFriendNotificationBinding>(onClick) {

    override fun bindData(data: ItemFriend, binding: ItemFriendNotificationBinding) {
        binding.tvName.text = data.name
        binding.tvStatus.text = data.status

        if (data.imgUrl.isNotEmpty()) {
            binding.ivIcon.setCircleImgUrl(data.imgUrl)
        } else {
            binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
        }

        binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
    }

    override fun getLayoutId(): Int = R.layout.item_friend_notification
}

class AddFriendAdapter(onClick: OnItemClickListener<ItemAddFriend>)
    : LazyAdapter<ItemAddFriend, ItemAddFriendBinding>(onClick) {

    override fun bindData(data: ItemAddFriend, binding: ItemAddFriendBinding) {
        binding.tvName.text = data.name

        if (data.imgUrl.isNotEmpty()) {
            binding.ivIcon.setCircleImgUrl(data.imgUrl)
        } else {
            binding.ivIcon.setCircleImgResource(R.drawable.ic_launcher_background)
        }

        binding.addIcon.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
    }

    override fun getLayoutId(): Int = R.layout.item_add_friend
}