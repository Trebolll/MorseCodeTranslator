package com.example.morsecodetranslatorapp

import com.example.morsecodetranslatorapp.ui.theme.MorseCodeTranslator
import org.junit.Assert.*
import org.junit.Test

class MorseCodeTranslatorTest {

    @Test
    fun testTextToMorse() {
        val result = MorseCodeTranslator.textToMorse("HELLO")
        assertTrue(result.isSuccess)
        assertEquals(".... . .-.. .-.. ---", result.getOrNull())
    }

    @Test
    fun testTextToMorseLowerCase() {
        val result = MorseCodeTranslator.textToMorse("hello")
        assertTrue(result.isSuccess)
        assertEquals(".... . .-.. .-.. ---", result.getOrNull())
    }

    @Test
    fun testMorseToText() {
        val result = MorseCodeTranslator.morseToText(".... . .-.. .-.. ---")
        assertTrue(result.isSuccess)
        assertEquals("HELLO", result.getOrNull())
    }

    @Test
    fun testInvalidMorseCode() {
        val result = MorseCodeTranslator.morseToText(".... . .-.. .-.. --- ...")
        assertTrue(result.isFailure)
    }

    @Test
    fun testInvalidText() {
        val result = MorseCodeTranslator.textToMorse("HELLO!")
        assertTrue(result.isFailure)
    }

    @Test
    fun testTextToMorseWithSpaces() {
        val result = MorseCodeTranslator.textToMorse("HELLO WORLD")
        assertTrue(result.isSuccess)
        assertEquals(".... . .-.. .-.. --- / .-- --- .-. .-.. -..", result.getOrNull())
    }

    @Test
    fun testMorseToTextWithSpaces() {
        val result = MorseCodeTranslator.morseToText(".... . .-.. .-.. --- / .-- --- .-. .-.. -..")
        assertTrue(result.isSuccess)
        assertEquals("HELLO WORLD", result.getOrNull())
    }

    @Test
    fun testTextToMorseWithNumbers() {
        val result = MorseCodeTranslator.textToMorse("123")
        assertTrue(result.isSuccess)
        assertEquals(".---- ..--- ...--", result.getOrNull())
    }

    @Test
    fun testMorseToTextWithNumbers() {
        val result = MorseCodeTranslator.morseToText(".---- ..--- ...--")
        assertTrue(result.isSuccess)
        assertEquals("123", result.getOrNull())
    }
}