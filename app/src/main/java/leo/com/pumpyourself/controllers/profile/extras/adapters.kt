package leo.com.pumpyourself.controllers.profile.extras

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.databinding.ItemProfileFriendsBinding


class FriendsAdapter(onClick: OnItemClickListener<ItemFriend>)
    : LazyAdapter<ItemFriend, ItemProfileFriendsBinding>(onClick) {

    override fun bindData(data: ItemFriend, binding: ItemProfileFriendsBinding) {
        binding.tvName.text = data.name
        binding.tvStatus.text = data.status
        binding.cvContainer.setOnClickListener { itemClickListener?.onLazyItemClick(data) }
    }

    override fun getLayoutId(): Int = R.layout.item_profile_friends

}