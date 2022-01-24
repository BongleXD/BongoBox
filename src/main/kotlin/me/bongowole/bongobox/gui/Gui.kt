package me.bongowole.bongobox.gui

import me.bongowole.bongobox.feature.Feature

abstract class Gui constructor(var locX: Float = 5f,
                               var locY: Float = 5f) : Feature {

    abstract fun render()
}