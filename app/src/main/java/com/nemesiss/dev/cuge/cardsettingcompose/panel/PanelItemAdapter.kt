package com.nemesiss.dev.cuge.cardsettingcompose.panel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nemesiss.dev.cuge.cardsettingcompose.R
import com.nemesiss.dev.cuge.cardsettingcompose.panel.holder.BannerItemViewHolder
import com.nemesiss.dev.cuge.cardsettingcompose.panel.holder.CardItemViewHolder
import com.nemesiss.dev.cuge.cardsettingcompose.panel.holder.ItemSpecHolder
import com.nemesiss.dev.cuge.cardsettingcompose.panel.holder.ListItemViewHolder
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.ItemSpec
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.SpecType

internal class PanelItemAdapter<T : ItemSpec>() :
        ListAdapter<T, ItemSpecHolder<T>>(ItemSpecDiffer()) {

    private class ItemSpecDiffer<T : ItemSpec> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.specType == newItem.specType
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSpecHolder<T> {
        return when (viewType) {
            SpecType.BannerItem.ordinal ->
                BannerItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false))

            SpecType.CardItem.ordinal ->
                CardItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))

            SpecType.ListItem.ordinal ->
                ListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

            else -> throw IllegalArgumentException("viewType: $viewType is invalid.")
        } as ItemSpecHolder<T>
    }

    override fun onBindViewHolder(holder: ItemSpecHolder<T>, position: Int) {
        holder.applySpec(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).specType.ordinal
    }
}

