package me.bongowole.bongobox.listener

import me.bongowole.bongobox.config.Config
import me.bongowole.bongobox.feature.Features
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class RenderListener {

    private var ticks = 1

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onRender(e: RenderGameOverlayEvent.Post) {
        if(ticks > Config.ticks) {
            Features.renderGui()
            ticks = 1
        } else ticks++
    }

}