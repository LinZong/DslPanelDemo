package com.nemesiss.dev.cuge.cardsettingcompose.panel.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.ItemSpec

internal abstract class ItemSpecHolder<in T : ItemSpec>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected companion object {
        val POSITIVE_RES_ID: (Int) -> Boolean = { resId -> resId != -1 }
    }

    abstract fun applySpec(spec: T)
}