package com.nemesiss.dev.cuge.cardsettingcompose.panel.holder

import android.util.TypedValue
import android.view.View
import com.nemesiss.dev.cuge.cardsettingcompose.databinding.ListItemBinding
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.ListItemSpec

internal class ListItemViewHolder(itemView: View) : ItemSpecHolder<ListItemSpec>(itemView) {

    private val binding = ListItemBinding.bind(itemView)

    override fun applySpec(spec: ListItemSpec) {
        binding.apply {
            itemIcon.setImageResource(spec.iconRes)
            itemTitle.text = spec.title

            spec.background.takeIf(POSITIVE_RES_ID)?.apply {
                val a = TypedValue()
                root.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, a, true)
                root.setBackgroundResource(a.resourceId)
            }
            spec.iconTint
                .takeIf(POSITIVE_RES_ID)
                ?.apply { itemIcon.imageTintList = root.context.getColorStateList(this) }

            val onClick = spec.onClick
            if (onClick != null) {
                root.setOnClickListener { onClick() }
            } else {
                root.setOnClickListener(null)
            }
        }
    }
}