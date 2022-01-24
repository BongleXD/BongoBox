package me.bongowole.bongobox

import me.bongowole.bongobox.feature.Features
import me.bongowole.bongobox.feature.dungeon.TerminalHelper
import me.bongowole.bongobox.listener.FeatureListener
import me.bongowole.bongobox.listener.RenderListener
import me.bongowole.bongobox.listener.StatusListener
import me.bongowole.bongobox.util.Utils
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

@Mod(
    modid = BongoBox.MOD_ID,
    name = BongoBox.MOD_NAME,
    version = BongoBox.VERSION,
    acceptedMinecraftVersions = "[1.8.9]",
    clientSideOnly = true
)
class BongoBox {

    var verbose: Boolean = false
    val pool: ThreadPoolExecutor

    init {
        instance = this
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1) as ThreadPoolExecutor
    }

    companion object {
        const val MOD_ID = "bongobox"
        const val MOD_NAME = "BongoBox"
        const val VERSION = "1"
        lateinit var instance: BongoBox
            private set
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {

    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        StatusListener()
        Features.enabledFeatureList.add(TerminalHelper())
        RenderListener()
        FeatureListener()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {

    }
    
}

fun debug(message: Any) {
    if (instance().verbose) Utils.chat("§c[BongoBox] §f$message")
}

fun instance() = BongoBox.instance