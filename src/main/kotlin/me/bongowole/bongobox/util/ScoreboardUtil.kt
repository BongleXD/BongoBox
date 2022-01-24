/*
 * Skytils - Hypixel Skyblock Quality of Life Mod
 * Copyright (C) 2022 Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.bongowole.bongobox.util

import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.EnumChatFormatting

object ScoreboardUtil {

    fun cleanSB(scoreboard: String): String {
        return EnumChatFormatting.getTextWithoutFormattingCodes(scoreboard).toCharArray().filter { it.code in 21..126 }.joinToString(separator = "")
    }

    var sidebarLines: List<String> = emptyList()

    fun fetchScoreboardLines(): List<String> {
        val mc = Minecraft.getMinecraft()
        val scoreboard = mc.theWorld?.scoreboard ?: return emptyList()
        val objective = scoreboard.getObjectiveInDisplaySlot(1) ?: return emptyList()
        var scores = scoreboard.getSortedScores(objective)
        val list = scores.filter {
            it != null && it.playerName != null && !it.playerName
                .startsWith("#")
        }
        scores = if (list.size > 15) {
            list.drop(15)
        } else {
            list
        }
        return scores.map {
            ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(it.playerName), it.playerName)
        }
    }
}