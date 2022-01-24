package me.bongowole.bongobox.listener

import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import me.bongowole.bongobox.debug
import me.bongowole.bongobox.util.ScoreboardUtil
import me.bongowole.bongobox.util.Utils
import me.bongowole.bongobox.util.keepScoreboardCharacters
import me.bongowole.bongobox.util.stripColor
import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.stream.Collectors

class StatusListener {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    private var tick = 1;

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (e.phase == TickEvent.Phase.START) {
            tick++
            val mc = Minecraft.getMinecraft()
            ScoreboardUtil.sidebarLines = ScoreboardUtil.fetchScoreboardLines().map { ScoreboardUtil.cleanSB(it) }
            if(mc?.theWorld != null && !mc.isSingleplayer && Utils.isOnHypixel) {
                Utils.isOnSkyblock = Utils.isOnHypixel && mc.theWorld.scoreboard.getObjectiveInDisplaySlot(1)
                    ?.let { ScoreboardUtil.cleanSB(it.displayName).contains("SKYBLOCK") } ?: false
                Utils.isInDungeon = ScoreboardUtil.sidebarLines.any {
                    (it.contains("The Catacombs") && !it.contains("Queue")) || it.contains("Dungeon Cleared:")
                }
            }
        } else if (tick > 20) tick = 1
    }
}