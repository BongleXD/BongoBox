package me.bongowole.bongobox.config

import java.lang.reflect.Field

object Config {

    @Property(name = "Refresh Rate", type = PropertyType.SLIDER,
        description = "Refresh Rate for render huds.\n" +
                "§fLower Value - Render: §aFast §7|  §fPerformance Cost: §cHigh" +
                "§fHigher Value - Render: §cSlow §7|  §fPerformance Cost: §aLow",
        category = "Settings",
        subcategory = "HUD",
        min =  0, max = 60)
    var ticks = 20

    @Property(name = "Show Dungeon Info", type = PropertyType.SWITCH,
        description = "Show Dungeon Information and  estimate of the current dungeon score when join dungeon." +
                " §cOnly Shows in Dungeon",
        category = "Dungeons",
        subcategory = "HUD")
    var showDungeonInfo = false

    @Property(name = "Terminal No Latency", type = PropertyType.SWITCH,
        description = "Removes most latency when solving terminals." +
                " §cMay bannable in Dungeons",
        category = "Dungeons",
        subcategory = "Terminals")
    var terminalNoLatency = false

    @Property(name = "Ghost Pickaxe", type = PropertyType.SWITCH,
        description = "Makes block disappear when you hold an pickaxe right click on blocks." +
                " §cMay bannable in Dungeons",
        category = "Dungeons",
        subcategory = "Quality Of Life")
    var ghostPickaxe = false

}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class Property (

    val type: PropertyType,
    val name: String,
    val category: String,
    val subcategory: String = "",
    val description: String = "",
    val searchTags: Array<String> = [],
    /**
     * [PropertyType.SLIDER] | [PropertyType.NUMBER]
     */
    val min: Int = 0,
    /**
     * [PropertyType.SLIDER] | [PropertyType.NUMBER]
     */
    val max: Int = 0,
    /**
     * [PropertyType.DECIMAL_SLIDER]
     */
    val minFloat: Float = 0f,
    /**
     * [PropertyType.DECIMAL_SLIDER]
     */
    val maxFloat: Float = 0f,
    /**
     * [PropertyType.DECIMAL_SLIDER]
     */
    val decimalPlaces: Int = 1,
    /**
     * [PropertyType.NUMBER]
     */
    val increment: Int = 1,
    /**
     *  [PropertyType.SELECTOR]
     */
    val options: Array<String> = []

)

enum class PropertyType(val type: Class<*>) {

    TEXT(String::class.java),
    SELECTOR(Int::class.java),
    BUTTON(Nothing::class.java),
    SWITCH(Boolean::class.java),
    NUMBER(Int::class.java),
    SLIDER(Int::class.java),
    PERCENT_SLIDER(Float::class.java),
    DECIMAL_SLIDER(Float::class.java);

    fun isFieldValid(field: Field): Boolean = field.type == type

}