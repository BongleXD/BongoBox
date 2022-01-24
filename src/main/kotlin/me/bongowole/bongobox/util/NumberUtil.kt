package me.bongowole.bongobox.util

fun Boolean.ifTrueInt(a: Int, b: Int = 0): Int = if(this) a else b