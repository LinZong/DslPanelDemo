package com.nemesiss.dev.cuge.cardsettingcompose.panel.holder

import android.view.View
import com.nemesiss.dev.cuge.cardsettingcompose.databinding.CardItemBinding
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.CardItemSpec

internal class CardItemViewHolder(itemView: View) : ItemSpecHolder<CardItemSpec>(itemView) {

    private val binding = CardItemBinding.bind(itemView)

    private var lastBackground = -1

    override fun applySpec(spec: CardItemSpec) {

        binding.apply {
            cardIcon.setImageResource(spec.iconRes)
            cardTitle.text = spec.title
            cardSubtitle.visibility = if (spec.subTitle.isEmpty()) View.GONE else View.VISIBLE
            cardSubtitle.text = spec.subTitle

            spec.background
                .takeIf(POSITIVE_RES_ID)
                ?.takeIf { curr -> lastBackground != curr }
                ?.apply {
                    cardWrapper.setBackgroundResource(this);
                    lastBackground = this
                }

            spec.iconTint.takeIf(POSITIVE_RES_ID)
                ?.apply { cardIcon.imageTintList = root.context.getColorStateList(this) }

            val onClick = spec.onClick
            if (onClick != null) {
                cardWrapper.setOnClickListener { onClick() }
            } else {
                cardWrapper.setOnClickListener(null)
            }
        }
    }
}