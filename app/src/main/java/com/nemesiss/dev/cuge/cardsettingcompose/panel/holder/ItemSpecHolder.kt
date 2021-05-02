package com.nemesiss.dev.cuge.cardsettingcompose.panel.holder

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.ItemSpec

internal abstract class ItemSpecHolder<in T : ItemSpec>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected companion object {
        val POSITIVE_RES_ID: (Int) -> Boolean = { resId -> resId != -1 }
    }

    abstract fun applySpec(spec: T)

    protected fun resolveAttribute(context: Context, attrResId: Int): Int {
        val a = TypedValue()
        context.theme.resolveAttribute(attrResId, a, true)
        return a.resourceId
    }

    protected fun handleBackground(view: View, backgroundResId: Int) {
        if (backgroundResId == android.R.attr.selectableItemBackground) {
            view.setBackgroundResource(resolveAttribute(view.context, backgroundResId))
        } else {
            view.setBackgroundResource(backgroundResId)
        }
    }
}