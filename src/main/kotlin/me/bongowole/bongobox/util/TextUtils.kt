package me.bongowole.bongobox.util

import java.text.DecimalFormat
import java.util.regex.Pattern

private val STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]")
private val NUMBERS_SLASHES = Pattern.compile("[^0-9 /]")
private val SCOREBOARD_CHARACTERS = Pattern.compile("[^a-z A-Z:0-9/'.]")
private val FLOAT_CHARACTERS = Pattern.compile("[^.0-9\\-]")
private val INTEGER_CHARACTERS = Pattern.compile("[^0-9]")
private val DECIMAL_FORMAT = DecimalFormat("#,###.##")

fun Double.toCommas(): String {
    return DECIMAL_FORMAT.format(this).removeSuffix(".00").removeSuffix(".0")
}

fun Int.toCommas(): String {
    return this.toDouble().toCommas()
}


/**
 * Strips color codes from a given text
 *
 * @return Text without color codes
 */
fun String.stripColor(): String {
    return STRIP_COLOR_PATTERN.matcher(this).replaceAll("")
}

/**
 * Removes any character that isn't a number, letter, or common symbol from a given text.
 *
 * @return Input text with only letters and numbers
 */
fun String.keepScoreboardCharacters(): String {
    return SCOREBOARD_CHARACTERS.matcher(this).replaceAll("")
}

/**
 * Removes any character that isn't a number, - or . from a given text.
 *
 * @return Input text with only valid float number characters
 */
fun String.keepFloatCharactersOnly(): String {
    return FLOAT_CHARACTERS.matcher(this).replaceAll("")
}

/**
 * Removes any character that isn't a number from a given text.
 *
 * @return Input text with only valid integer number characters
 */
fun String.keepIntegerCharactersOnly(): String {
    return INTEGER_CHARACTERS.matcher(this).replaceAll("")
}

/**
 * Removes any character that isn't a number from a given text.
 *
 * @return Input text with only numbers
 */
fun String.getNumbersOnly(): String {
    return NUMBERS_SLASHES.matcher(this).replaceAll("")
}

/**
 * Removes any duplicate spaces from a given text.
 *
 * @return Input text without repeating spaces
 */
fun removeDuplicateSpaces(text: String): String {
    return text.replace("\\s+".toRegex(), " ")
}

/**
 * Reverses a given text while leaving the english parts intact and in order.
 * (Maybe its more complicated than it has to be, but it gets the job done.)
 *
 * @return Reversed input text
 */
fun String.reverseText(): String {
    val newString = StringBuilder()
    val parts = this.split(" ".toRegex()).toTypedArray()
    for (i in parts.size downTo 1) {
        val textPart = parts[i - 1]
        var foundCharacter = false
        for (letter in textPart.toCharArray()) {
            if (letter.code > 191) { // Found special character
                foundCharacter = true
                newString.append(StringBuilder(textPart).reverse().toString())
                break
            }
        }
        newString.append(" ")
        if (!foundCharacter) {
            newString.insert(0, textPart)
        }
        newString.insert(0, " ")
    }
    return removeDuplicateSpaces(newString.toString().trim { it <= ' ' })
}

/**
 * Get the ordinal suffix of a number, meaning
 *
 *  * st - if n ends with 1 but isn't 11
 *  * nd - if n ends with 2 but isn't 12
 *  * rd - if n ends with 3 but isn't 13
 *  * th - in all other cases
 *
 */
fun Int.getOrdinalSuffix(): String {
    return if (this in 11..13) {
        "th"
    } else when (this % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}