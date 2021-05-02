package com.nemesiss.dev.cuge.cardsettingcompose.panel.holder

import android.view.View
import com.nemesiss.dev.cuge.cardsettingcompose.databinding.BannerItemBinding
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.BannerItemSpec

internal class BannerItemViewHolder(itemView: View) :
        ItemSpecHolder<BannerItemSpec>(itemView) {

    private val binding = BannerItemBinding.bind(itemView)


    override fun applySpec(spec: BannerItemSpec) {

        binding.apply {
            bannerLogo.setImageResource(spec.iconRes)
            bannerText.text = spec.title
            spec.background.takeIf { resId -> resId != -1 }?.apply {
                handleBackground(root, this)
            }
            val onClick = spec.onClick
            if (onClick != null) {
                root.apply {
                    isClickable = true
                    isFocusable = true
                    setOnClickListener { onClick() }
                }
            } else {
                root.apply {
                    isClickable = false
                    isFocusable = false
                    setOnClickListener(null)
                }
            }
        }
    }
}