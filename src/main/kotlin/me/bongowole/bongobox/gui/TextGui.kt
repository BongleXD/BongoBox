package me.bongowole.bongobox.gui

import me.bongowole.bongobox.util.RenderUtil
import java.awt.Color

open class TextGui constructor(var text: MutableList<String> = mutableListOf(),
                               var shadow: Boolean = false) : Gui() {

    override fun render() {
        RenderUtil.drawText(text, locX, locY, color = Color.WHITE.rgb, shadow, 0.7f)
    }

}