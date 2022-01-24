package me.bongowole.bongobox.util

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import java.awt.Color

object RenderUtil {

    fun drawText(text: List<String>, x: Float, y: Float, color: Int, shadow: Boolean, scale: Float) {
        var y1 = y
        text.forEach {
            drawText(it, x, y1, color, shadow, scale)
            y1 += 9f * scale
        }
    }

    fun drawText(text: String, x: Float, y: Float, color: Int, shadow: Boolean, scale: Float) {
        GlStateManager.pushMatrix()
        GlStateManager.translate(x, y, 0f)
        GlStateManager.scale(scale, scale, 1f)
        val mc = Minecraft.getMinecraft()
        if (shadow) {
            val colorAlpha = color.getAlpha().coerceAtLeast(4)
            val colorBlack: Int = Color(0f, 0f, 0f, colorAlpha / 255f).rgb
            val strippedText: String = text.stripColor()
            mc.fontRendererObj.drawString(strippedText, 1f, 0f, colorBlack, false)
            mc.fontRendererObj.drawString(strippedText, -1f, 0f, colorBlack, false)
            mc.fontRendererObj.drawString(strippedText, 0f, 1f, colorBlack, false)
            mc.fontRendererObj.drawString(strippedText, 0f, -1f, colorBlack, false)
            mc.fontRendererObj.drawString(text, 0f, 0f, color, false)
        } else {
            mc.fontRendererObj.drawString(text, 0f, 0f, color, true)
        }
        GlStateManager.popMatrix()
    }
}

fun Int.getAlpha(): Int =  this shr 24 and 255