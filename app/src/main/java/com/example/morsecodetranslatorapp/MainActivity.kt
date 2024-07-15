package com.example.morsecodetranslatorapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.AnnotatedString
import com.example.morsecodetranslatorapp.ui.theme.MorseCodeTranslatorAppTheme
import com.example.morsecodetranslatorapp.ui.theme.MorseCodeTranslator
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorseCodeTranslatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TranslatorScreen()
                }
            }
        }

        // Инициализация и запуск MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.isLooping = true // Зациклить музыку, если необходимо
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Освобождение ресурсов MediaPlayer
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TranslatorScreen() {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    fun translate() {
        val inputText = input.text.trim()
        val validationError = validateTextInput(inputText)
        if (validationError != null) {
            errorMessage = validationError
        } else {
            if (MorseCodeTranslator.isMorseCode(inputText)) {
                val resultOrError = MorseCodeTranslator.morseToText(inputText)
                resultOrError.fold(
                    onSuccess = { result = it },
                    onFailure = { errorMessage = it.message }
                )
            } else {
                val resultOrError = MorseCodeTranslator.textToMorse(inputText)
                resultOrError.fold(
                    onSuccess = { result = it },
                    onFailure = { errorMessage = it.message }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // Изменено на Top для выравнивания сверху
    ) {
        Text(
            text = "Используйте символы . и - для кода Морзе.\n" +
                    "Используйте буквы русского и английского алфавитов и цифры для текста.",
            color = Color(0xFF4B0082), // Темно-фиолетовый цвет
            fontFamily = FontFamily.Cursive, // Рукописный шрифт
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text("Результат: $result", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = input,
            onValueChange = {
                input = it
                errorMessage = null // Сброс сообщения об ошибке при изменении ввода
            },
            label = { Text("Введите текст или код Морзе") },
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Enter) {
                        translate()
                        true
                    } else {
                        false
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { translate() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Перевести")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                clipboardManager.setText(AnnotatedString(result))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Скопировать результат")
        }
    }
}

fun validateTextInput(input: String): String? {
    val validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ0123456789 ,.!?@()-/"
    input.uppercase(Locale.ROOT).forEach { char ->
        if (char !in validChars) {
            return "Недопустимый символ: $char"
        }
    }
    return null
}