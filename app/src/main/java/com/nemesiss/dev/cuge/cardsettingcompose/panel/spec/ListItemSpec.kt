package com.nemesiss.dev.cuge.cardsettingcompose.panel.spec

class ListItemSpec(override val iconRes: Int,
                   override val title: String,
                   override val background: Int = -1,
                   override val iconTint: Int = -1,
                   override val onClick: (() -> Unit)? = null) : ItemSpec() {

    override val specType: SpecType = SpecType.ListItem

    class Builder : ItemSpec.Builder() {
        var iconRes: Int = -1
        var title: String = ""
        var background: Int = -1
        var iconTint: Int = -1
        var onClick: (() -> Unit)? = null

        override fun <T : ItemSpec> checkParams(ok: () -> T): T {
            if (iconRes == -1) {
                throw IllegalArgumentException("iconRes must be set!")
            }
            return ok()
        }

        fun build() = checkParams { ListItemSpec(iconRes, title, background, iconTint, onClick) }
    }
}