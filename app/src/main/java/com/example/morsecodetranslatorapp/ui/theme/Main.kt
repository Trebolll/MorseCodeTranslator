package com.example.morsecodetranslatorapp.ui.theme

import com.example.morsecodetranslatorapp.ui.theme.MorseCodeTranslator.isMorseCode
import com.example.morsecodetranslatorapp.ui.theme.MorseCodeTranslator.morseToText
import com.example.morsecodetranslatorapp.ui.theme.MorseCodeTranslator.textToMorse
import java.util.Scanner

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        println("Введите текст или код Морзе:")
        val input = scanner.nextLine().trim { it <= ' ' }

        if (isMorseCode(input)) {
            println("Результат: " + morseToText(input))
        } else {
            println("Результат: " + textToMorse(input))
        }
    }
}