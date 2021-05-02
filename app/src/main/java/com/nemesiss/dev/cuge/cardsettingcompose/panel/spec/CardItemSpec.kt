package com.nemesiss.dev.cuge.cardsettingcompose.panel.spec

class CardItemSpec(
    override val iconRes: Int,
    override val title: String,
    val subTitle: String,
    override val background: Int = -1,
    override val iconTint: Int = -1,
    override val onClick: (() -> Unit)? = null) : ItemSpec() {

    override val specType: SpecType = SpecType.CardItem

    class Builder : ItemSpec.Builder() {
        var iconRes: Int = -1
        var title: String = ""
        var subTitle: String = ""
        var background: Int = -1
        var iconTint: Int = -1
        var onClick: (() -> Unit)? = null

        override fun <T : ItemSpec> checkParams(ok: () -> T): T {
            if (iconRes == -1) {
                throw IllegalArgumentException("iconRes must be set!")
            }
            return super.checkParams(ok)
        }

        fun build() = checkParams { CardItemSpec(iconRes, title, subTitle, background, iconTint, onClick) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardItemSpec) return false
        if (!super.equals(other)) return false

        if (subTitle != other.subTitle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + subTitle.hashCode()
        return result
    }
}