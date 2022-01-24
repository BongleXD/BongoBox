package me.bongowole.bongobox.util

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import java.util.regex.Pattern

object Utils {

    val isOnHypixel: Boolean
        get() {
            val pattern = Pattern.compile("(.+) <- (?:.+)")
            val brand = "Hypixel BungeeCord"
            val mc = Minecraft.getMinecraft()
            return if (!mc.isSingleplayer && mc.thePlayer.clientBrand != null) {
                val matcher = pattern.matcher(mc.thePlayer.clientBrand)
                if (matcher.find()) {
                    matcher.group(1).startsWith(brand)
                } else {
                    false
                }
            } else {
                false
            }
        }
    var isOnSkyblock = false
        get() = isOnHypixel && field
    var isInDungeon = false
        get() = isOnSkyblock && field

    fun chat(message: Any) = Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(message.toString()))
    
}