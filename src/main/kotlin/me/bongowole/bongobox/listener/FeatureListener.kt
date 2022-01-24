package me.bongowole.bongobox.listener

import me.bongowole.bongobox.feature.Features
import me.bongowole.bongobox.feature.InteractFeature
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class FeatureListener {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onInteract(e: PlayerInteractEvent) {
        Features.enabledFeatureList.filterIsInstance<InteractFeature>().forEach {
            it.onInteract(e)
        }
    }

}