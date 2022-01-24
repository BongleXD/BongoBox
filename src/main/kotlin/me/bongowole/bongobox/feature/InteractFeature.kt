package me.bongowole.bongobox.feature

import net.minecraftforge.event.entity.player.PlayerInteractEvent

abstract  class InteractFeature : Feature {

    abstract fun onInteract(e: PlayerInteractEvent)

}