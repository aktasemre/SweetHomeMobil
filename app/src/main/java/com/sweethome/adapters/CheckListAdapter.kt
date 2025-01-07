package com.sweethome.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sweethome.databinding.ItemCheckListBinding
import com.sweethome.models.CheckItem

class CheckListAdapter(
    private val onItemClick: (CheckItem) -> Unit
) : ListAdapter<CheckItem, CheckListAdapter.ViewHolder>(CheckItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCheckListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemCheckListBinding,
        private val onItemClick: (CheckItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CheckItem) {
            binding.titleText.text = item.title
            binding.descriptionText.text = item.description
            
            Glide.with(binding.root)
                .load(item.photoUrl)
                .into(binding.photoImage)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}

class CheckItemDiffCallback : DiffUtil.ItemCallback<CheckItem>() {
    override fun areItemsTheSame(oldItem: CheckItem, newItem: CheckItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CheckItem, newItem: CheckItem): Boolean {
        return oldItem == newItem
    }
} 