package com.example.morsecodetranslatorapp.ui.theme

object MorseCodeTranslator {

    private val morseCodeMap = mapOf(
        // Пример карты символов Морзе
        ".-" to 'A', "-..." to 'B', "-.-." to 'C', "-.." to 'D', "." to 'E',
        "..-." to 'F', "--." to 'G', "...." to 'H', ".." to 'I', ".---" to 'J',
        "-.-" to 'K', ".-.." to 'L', "--" to 'M', "-." to 'N', "---" to 'O',
        ".--." to 'P', "--.-" to 'Q', ".-." to 'R', "..." to 'S', "-" to 'T',
        "..-" to 'U', "...-" to 'V', ".--" to 'W', "-..-" to 'X', "-.--" to 'Y',
        "--.." to 'Z', "-----" to '0', ".----" to '1', "..---" to '2', "...--" to '3',
        "....-" to '4', "....." to '5', "-...." to '6', "--..." to '7', "---.." to '8',
        "----." to '9',
        // Русский алфавит
        ".-" to 'А', "-..." to 'Б', ".--" to 'В', "--." to 'Г', "-.." to 'Д', "." to 'Е',
        "...-" to 'Ж', "--.." to 'З', ".." to 'И', ".---" to 'Й', "-.-" to 'К', ".-.." to 'Л',
        "--" to 'М', "-." to 'Н', "---" to 'О', ".--." to 'П', ".-." to 'Р', "..." to 'С',
        "-" to 'Т', "..-" to 'У', "..-." to 'Ф', "...." to 'Х', "-.-." to 'Ц', "---." to 'Ч',
        "----" to 'Ш', "--.-" to 'Щ', ".--.-." to 'Ъ', "-.--" to 'Ы', "-..-" to 'Ь', "..-.." to 'Э',
        "..--" to 'Ю', ".-.-" to 'Я',
        // Дополнительные символы
        "--..--" to ',', ".-.-.-" to '.', "..--.." to '?', "-.-.--" to '!', "-....-" to '-', "-..-." to '/', ".--.-." to '@', "-.--.-" to ')', "-.--." to '('
    )

    private val reversedMorseCodeMap = morseCodeMap.entries.associate { (k, v) -> v to k }

    fun isMorseCode(input: String): Boolean {
        return input.trim().split(" ").all { it in morseCodeMap.keys || it == "/" }
    }

    fun morseToText(morse: String): Result<String> {
        val result = morse.trim().split(" / ").map { word ->
            word.split(" ").map { code ->
                morseCodeMap[code] ?: return Result.failure(IllegalArgumentException("Недопустимый символ: $code"))
            }.joinToString("")
        }.joinToString(" ")
        return Result.success(result)
    }

    fun textToMorse(text: String): Result<String> {
        val result = text.trim().toUpperCase().split(" ").map { word ->
            word.map { char ->
                reversedMorseCodeMap[char] ?: return Result.failure(IllegalArgumentException("Недопустимый символ: $char"))
            }.joinToString(" ")
        }.joinToString(" / ") // Символ / для разделения слов
        return Result.success(result)
    }
}