package me.bongowole.bongobox.feature.dungeon

import me.bongowole.bongobox.config.Config
import me.bongowole.bongobox.feature.InteractFeature
import me.bongowole.bongobox.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraftforge.event.entity.player.PlayerInteractEvent

class TerminalHelper : InteractFeature() {

    override fun onInteract(e: PlayerInteractEvent) {
        if (Config.ghostPickaxe && Utils.isInDungeon && e.action.name.contains("RIGHT")) {
            val mc = Minecraft.getMinecraft()
            val item = mc.thePlayer.heldItem
            if (mc.theWorld != null && item?.item?.unlocalizedName?.contains("pickaxe") == true) {
                val blockPos = mc.objectMouseOver.blockPos ?: return
                val block = mc.theWorld.getBlockState(blockPos) ?: return
                val interactables = listOf(
                    Blocks.acacia_door,
                    Blocks.anvil,
                    Blocks.beacon,
                    Blocks.bed,
                    Blocks.birch_door,
                    Blocks.brewing_stand,
                    Blocks.command_block,
                    Blocks.crafting_table,
                    Blocks.chest,
                    Blocks.dark_oak_door,
                    Blocks.daylight_detector,
                    Blocks.daylight_detector_inverted,
                    Blocks.dispenser,
                    Blocks.dropper,
                    Blocks.enchanting_table,
                    Blocks.ender_chest,
                    Blocks.furnace,
                    Blocks.hopper,
                    Blocks.jungle_door,
                    Blocks.lever,
                    Blocks.noteblock,
                    Blocks.powered_comparator,
                    Blocks.unpowered_comparator,
                    Blocks.powered_repeater,
                    Blocks.unpowered_repeater,
                    Blocks.standing_sign,
                    Blocks.wall_sign,
                    Blocks.trapdoor,
                    Blocks.trapped_chest,
                    Blocks.wooden_button,
                    Blocks.stone_button,
                    Blocks.oak_door,
                    Blocks.skull
                )
                if(!interactables.contains(block.block)){
                    mc.theWorld.setBlockToAir(blockPos)
                    e.isCanceled = true
                }
            }
        }
    }

}