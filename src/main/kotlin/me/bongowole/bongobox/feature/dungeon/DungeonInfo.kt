package me.bongowole.bongobox.feature.dungeon

import me.bongowole.bongobox.gui.TextGui
import me.bongowole.bongobox.util.getNumbersOnly
import me.bongowole.bongobox.util.ifTrueInt
import me.bongowole.bongobox.util.toCommas
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class DungeonInfo constructor(val floor: Floor) : TextGui() {

    var score: Score = Score()
    var crypt: Int = 0
    var mimic: Boolean = false
    val roomTotal
        get() = if (clearPercentage > 0)
                (100f / clearPercentage.toDouble() * calcRoomComplete.toDouble()).toInt() else 0
    var roomComplete: Int = 0
    val calcRoomComplete
        get() = bloodClear.ifTrueInt(1) + (!bossEnter).ifTrueInt(1) + roomComplete
    var clearPercentage: Int = 0
    val trueClearPercentage
        get() = (if (roomTotal > 0) calcRoomComplete.toDouble() / roomTotal.toDouble() else 0.0).coerceAtMost(1.0)
    val secretTotal
        get() = floor(100f / secretPercentage * secretFound.toDouble() + 0.5)
    var secretFound: Int = 0
    var secretPercentage: Double = 0.0
    val secretReq
        get() = ceil(secretTotal * floor.secretReq).toInt()
    val secretReqPercentage
        get() = (secretFound.toDouble() / secretReq).coerceAtMost(1.0)
    var death: Int = 0
    var spirit: Boolean = false
    var puzzleTotal: Int = 0
    var puzzleMissing: Int = 0
    var puzzleFailed: Int = 0
    var ezpzCandidate: Boolean = false
    var actualTime: Long = 0L
    var inGameTime: Int = 0
    var speedFunc: (Int) -> Double = when (floor.thresholdMin) {
        12 -> { {
            when {
                it < 14 -> 100.0
                it <= 140 -> 100 - 1 / 14.0 * it
                it <= 420 -> 95 - 1 / 28.0 * it
                it <= 780 -> 92 - 1 / 36.0 * it
                it <= 1200 -> 87 - 1 / 45.0 * it
                else -> 85 - 1 / 50.0 * it
            }
        } }
        10 -> { {
            when {
                it < 12 -> 100.0
                it <= 120 -> 100 - 1 / 12.0 * it
                it <= 360 -> 95 - 1 / 24.0 * it
                it <= 660 -> 92 - 1 / 30.0 * it
                it <= 1020 -> 88.333 - 1 / 36.0 * it
                else -> 84.286 - 1 / 42.0 * it
            }
        } }
        else ->  { {
            when {
                it < 10 -> 100.0
                it <= 100 -> 100 - 0.1 * it
                it <= 300 -> 95 - 0.05 * it
                it <= 550 -> 92 - 0.04 * it
                it <= 850 -> 87 - 1 / 30.0 * it
                else -> 85 - 1 / 35.0 * it
            }
        } }
    }
    var bloodClear: Boolean = false
    var bossEnter: Boolean = false
    val rank
        get() = when {
            score.total < 100 -> "§cD"
            score.total < 160 -> "§9C"
            score.total < 230 -> "§aB"
            score.total < 270 -> "§5A"
            score.total < 300 -> "§eS"
            else -> "§6S+"
        }

    fun scoreUpdate() {
        val puzzlePenalty = 10 * (puzzleMissing + puzzleFailed)
        val deathPenalty = 2 * death - spirit.ifTrueInt(1)
        score.roomClear = floor(60 * trueClearPercentage).toInt()
        score.secretFound = floor(40 * secretReqPercentage).toInt()
        score.skill = (20f + trueClearPercentage * 80f - puzzlePenalty - deathPenalty).toInt().coerceAtLeast(20)
        score.speed = speedFunc((actualTime / 1000 - floor.thresholdMin * 60).toInt()).roundToInt()
        score.bonus = crypt.coerceAtMost(5) + mimic.ifTrueInt(2) + ezpzCandidate.ifTrueInt(10)
    }

    fun textUpdate() {
        text.clear()
        val floorText = "Catacomb " + floor.name.removePrefix("CATA_")
        val puzzleMissingOrFailCount = puzzleMissing + puzzleFailed
        val exploreScore = score.roomClear + score.secretFound
        text.addAll(listOf(
            "§9$floorText Info",
            "§f• §eDeaths: ${(if (death > 0) "§c" else "§a") + death.toDouble().toCommas() + if (spirit) " §7(§6Spirit§7)" else ""}",
            "§f• §ePuzzles: ${(if (puzzleMissingOrFailCount == 0) "§a" else "§c") + (puzzleTotal - puzzleMissingOrFailCount)}§7/§a$puzzleTotal${if (puzzleFailed > 0) " §7(§cYikes: $puzzleFailed§7)" else ""}",
            if(secretFound == 0) "" else "§f• §eSecrets: ${(if (secretReqPercentage == 1.0) "§a" else "§c") + secretFound + "§7/§a" + ceil(secretTotal * floor.secretReq).toInt() + if(floor.secretReq < 1.0) " §7(§6Total: $secretTotal§7)" else ""}",
            "§f• §eCrypts: ${(if(crypt < 5) "§c" else "§a") + crypt.coerceAtMost(5) + "§7/§a5" + if (crypt > 5) " §7(§bTotal: ${crypt.toDouble().toCommas()}§7)" else ""}"
        ))
        if (floor.name.getNumbersOnly().toInt() >= 6) text.add("§f• §eMimic: ${if (mimic) "§a✔" else "§c✘"}")
        text.addAll(listOf(
            "",
            "§6Score Approx",
            "§f• §eSkill: §a${score.skill}",
            "§f• §eExplore: §a${exploreScore}" + if (score.roomClear > 0 && score.secretFound >= 0) " §7(§6${score.roomClear} §7+ §e${score.secretFound}§7)" else if (score.roomClear > 0) " §7(§6${score.roomClear}§7)" else if (score.secretFound > 0) " §7(§e${score.secretFound}§7)" else "",
            "§f• §eSpeed: §a${score.speed}",
            "§f• §eBonus: §a${score.bonus}" + if (ezpzCandidate) " §7(§6+10§7)" else "",
            "§f• §eTotal: $rank ${score.total}",
        ))
    }

    override fun render() {
        scoreUpdate()
        textUpdate()
        super.render()
    }

}

class Score constructor(
    var speed: Int = 100,
    var roomClear: Int = 0,
    var secretFound: Int = 0,
    var skill: Int = 20,
    var bonus: Int = 0,
){
    val total
        get() = bonus + skill + speed + roomClear + secretFound
}

enum class Floor(val secretReq: Double = 1.0, val thresholdMin: Int = 10) {

    CATA_E(.3,),
    CATA_F1(.3),
    CATA_F2(.4),
    CATA_F3(.5),
    CATA_F4(.6),
    CATA_F5(.7, 12),
    CATA_F6(.85),
    CATA_F7(thresholdMin = 12),
    CATA_M1(thresholdMin = 8),
    CATA_M2(thresholdMin = 8),
    CATA_M3(thresholdMin = 8),
    CATA_M4(thresholdMin = 8),
    CATA_M5(thresholdMin = 8),
    CATA_M6(thresholdMin = 8),
    CATA_M7(thresholdMin = 8)

}