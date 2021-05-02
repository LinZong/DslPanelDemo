package com.nemesiss.dev.cuge.cardsettingcompose.panel.spec


enum class SpecType {
    ListItem,
    CardItem,
    BannerItem
}


abstract class ItemSpec {

    abstract val specType: SpecType

    abstract val iconRes: Int

    abstract val title: String

    abstract val iconTint: Int

    abstract val background: Int

    abstract val onClick: (() -> Unit)?

    abstract class Builder {
        open fun <T : ItemSpec> checkParams(ok: () -> T): T = ok()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemSpec) return false

        if (specType != other.specType) return false
        if (iconRes != other.iconRes) return false
        if (title != other.title) return false
        if (iconTint != other.iconTint) return false
        if (background != other.background) return false

        // onClick 不参加 equals计算。
        return true
    }

    override fun hashCode(): Int {
        var result = specType.hashCode()
        result = 31 * result + iconRes
        result = 31 * result + title.hashCode()
        result = 31 * result + iconTint
        result = 31 * result + background
        return result
    }
}