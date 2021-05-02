package com.nemesiss.dev.cuge.cardsettingcompose.panel

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.BannerItemSpec
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.CardItemSpec
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.ItemSpec
import com.nemesiss.dev.cuge.cardsettingcompose.panel.spec.ListItemSpec

abstract class SpecState

class EmptyState : SpecState()

class PanelItemScope<T : SpecState> private constructor(private var state: T,
                                                        private val dsl: PanelItemScope<T>.() -> Unit) {

    companion object {
        @JvmStatic
        fun <T : SpecState> PanelItems(initState: T, block: PanelItemScope<T>.() -> Unit): PanelItemScope<T> {
            val scope = PanelItemScope(initState, block)
            scope.runDsl()
            return scope
        }

        @JvmStatic
        fun PanelItems(block: PanelItemScope<EmptyState>.() -> Unit): PanelItemScope<EmptyState> {
            val scope = PanelItemScope(EmptyState(), block)
            scope.runDsl()
            return scope
        }

        private var containerId = Int.MAX_VALUE - 3939
    }

    private val banners = arrayListOf<BannerItemSpec>()
    private val cards = arrayListOf<CardItemSpec>()
    private val items = arrayListOf<ListItemSpec>()

    private var containerInitialized = false
    private lateinit var recyclerView: RecyclerView
    private val adapter = PanelItemAdapter<ItemSpec>()

    fun BannerItemSpec(block: BannerItemSpec.Builder.() -> Unit) {
        val builder = BannerItemSpec.Builder()
        block(builder)
        banners += builder.build()
    }

    fun CardItemSpec(block: CardItemSpec.Builder.() -> Unit) {
        val builder = CardItemSpec.Builder()
        block(builder)
        cards += builder.build()
    }

    fun ListItemSpec(block: ListItemSpec.Builder.() -> Unit) {
        val builder = ListItemSpec.Builder()
        block(builder)
        items += builder.build()
    }

    fun into(v: ViewGroup): PanelItemScope<T> {
        ensureContainerInitialized(v)
        val specs = collectSpecs()
        adapter.submitList(specs)
        return this
    }

    fun applyState(newState: T) {
        state = newState
        runDsl()
        val specs = collectSpecs()
        adapter.submitList(specs)
    }

    private fun ensureContainerInitialized(v: ViewGroup) {
        if (containerInitialized) return
        deduceContainerId(v)
        recyclerView = RecyclerView(v.context)
        // set container id for RecyclerView
        recyclerView.id = containerId
        recyclerView.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        // prevent blink when updating holder.
        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter
        v.addView(recyclerView)
        containerInitialized = true
    }


    private fun deduceContainerId(v: ViewGroup) {
        var prev: View? = v
        var next: View? = v
        // find the decorView
        while (next != null) {
            prev = next
            next = next.parent as? View
        }
        val decorView = prev?.findViewById<View>(android.R.id.content)
        // find a not-used containerId.
        var container = decorView?.findViewById<View>(containerId)
        while (container != null) {
            containerId--
            container = decorView?.findViewById(containerId)
        }
        require(containerId > 0) { "Cannot find a valid id for panel container! " }
    }

    private fun runDsl() {
        banners.clear()
        cards.clear()
        items.clear()
        dsl(this)
    }

    private fun collectSpecs(): List<ItemSpec> {
        val specs = ArrayList<ItemSpec>(banners.size + cards.size + items.size)
        specs += banners
        specs += cards
        specs += items
        banners.clear()
        cards.clear()
        items.clear()
        return specs
    }
}