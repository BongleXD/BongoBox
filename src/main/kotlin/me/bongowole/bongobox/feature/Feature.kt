package me.bongowole.bongobox.feature

import me.bongowole.bongobox.feature.dungeon.DungeonInfo
import me.bongowole.bongobox.feature.dungeon.TerminalHelper
import me.bongowole.bongobox.gui.Gui

enum class Features(val clazz: Class<out Feature>) {

    DUNGEON_INFO(DungeonInfo::class.java),
    TERMINAL_HELPER(TerminalHelper::class.java);

    fun isEnabled() : Boolean  {
        return enabledFeatureList.map { it::class.java }.contains(this.clazz)
    }

    companion object {
        val enabledFeatureList: MutableList<Feature> = mutableListOf()
        val guiFeatures: List<Gui>
            get() = enabledFeatureList.filterIsInstance<Gui>()

        fun renderGui() {
            enabledFeatureList.filterIsInstance<Gui>().forEach {
                it.render()
            }
        }
    }

}

interface Feature {

    fun isEnabled() {}

}