package com.example.superfruits.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfruits.R
import com.example.superfruits.ui.theme.SuperFruitsTheme
import java.util.Locale

@Composable
fun ManualScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit
) {
    val manualPagesWithNames = listOf(
        R.drawable.manual to "Manzana",
        R.drawable.manual_page2 to "Plátano",
        R.drawable.manual_page3 to "Naranja",
        R.drawable.manual_page4 to "Fresa",
        R.drawable.manual_page5 to "Kiwi",
        R.drawable.manual_page6 to "Mora azul",
        R.drawable.manual_page7 to "Brocoli",
        R.drawable.manual_page8 to "Zanahoria",
        R.drawable.manual_page9 to "Espinaca",
        R.drawable.manual_page10 to "Leche",
        R.drawable.manual_page11 to "Atún",
        R.drawable.manual_page12 to "Pollo",
        R.drawable.manual_page13 to "Huevo",
        R.drawable.manual_page14 to "Yogurt",
        R.drawable.manual_page15 to "Pescado",
        R.drawable.manual_page16 to "Tomate",
        R.drawable.manual_page17 to "Piña",
        R.drawable.manual_page18 to "Almendras",
        R.drawable.manual_page19 to "Aguacate",
        R.drawable.manual_page20 to "Arroz",
        R.drawable.manual_page21 to "Papas",
        R.drawable.manual_page22 to "Tortilla de maiz",
        R.drawable.manual_page23 to "Sandia",
        R.drawable.manual_page24 to "Elote",
        R.drawable.manual_page26 to "Frijoles",
        R.drawable.manual_page27 to "Garbanzos",
        R.drawable.manual_page28 to "Queso fresco",
        R.drawable.manual_page29 to "Apio",
        R.drawable.manual_page30 to "Avena"
    )

    val manualPages = manualPagesWithNames.map { it.first }
    val fruitNames = manualPagesWithNames.map { it.second }

    var currentPage by remember { mutableStateOf(0) }
    var isTransitioning by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("es", "MX")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown()
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (isTransitioning) 0f else 1f,
        animationSpec = tween(durationMillis = 250),
        finishedListener = {
            if (isTransitioning) {
                isTransitioning = false
            }
        },
        label = "fade_animation"
    )

    fun nextPage() {
        if (currentPage < manualPages.size - 1) {
            isTransitioning = true
            currentPage++
        }
    }

    fun previousPage() {
        if (currentPage > 0) {
            isTransitioning = true
            currentPage--
        }
    }

    fun speakFruitName() {
        tts?.speak(fruitNames[currentPage], TextToSpeech.QUEUE_FLUSH, null, null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6EFC4))
    ) {
        // Imagen de la página (SIN click)
        Image(
            painter = painterResource(id = manualPages[currentPage]),
            contentDescription = "Manual page ${currentPage + 1}",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            alpha = alpha
        )

        // Botón de audio - SOLO ESTE ES CLICKEABLE
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF17AFE4).copy(alpha = 0.9f))
                .clickable { speakFruitName() },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "🔊",
                fontSize = 30.sp
            )
        }
    }

    Image(
        painter = painterResource(id = R.drawable.home_button),
        contentDescription = "Home Button",
        modifier = Modifier
            .size(110.dp)
            .offset(x = 140.dp, y = 770.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigateToHome() },
        contentScale = ContentScale.Fit
    )

    Image(
        painter = painterResource(id = R.drawable.left_arrow),
        contentDescription = "Previous Page",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 50.dp, y = 800.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { previousPage() },
        contentScale = ContentScale.Fit,
        alpha = if (currentPage > 0) 1f else 0.3f
    )

    Image(
        painter = painterResource(id = R.drawable.right_arrow),
        contentDescription = "Next Page",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 285.dp, y = 800.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { nextPage() },
        contentScale = ContentScale.Fit,
        alpha = if (currentPage < manualPages.size - 1) 1f else 0.3f
    )
}

@Preview(showBackground = true)
@Composable
fun ManualScreenPreview() {
    SuperFruitsTheme {
        ManualScreen() { }
    }
}