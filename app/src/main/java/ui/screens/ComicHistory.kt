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
import androidx.compose.ui.graphics.graphicsLayer
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
fun ComicHistory(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit
) {
    // Asociar cada página con su texto
    val comicPagesWithText = listOf(
        R.drawable.history_page1 to "En la ciudad Fruit vive un perrito muy especial ....      Soy nutripup, he descubierto que la comida sana me da superpoderes! ¡Me siento mas fuerte que nunca!  Las zanahoria me da visión nocturna",
        R.drawable.history_page2 to "La misión de Nutripup!  Recorro ciudad fruti ayudando!  Atrapando ladrones y rescatando a los ciudadanos, elijo mis alimentos con cuidado todos los dias. ¡Y hago ejercicio!",
        R.drawable.history_page3 to "Un día inesperado: Atención ciudadanos de Fruitcity, Pronto infestare la ciudad con ratas, todo estará sucio y asqueroso",
        R.drawable.history_page4 to "Dr Junk: ¡Yo soy el malvado Dr. Junk , me quiero adueñar de ciudad Fruit. ¡Viva la suciedad!",
        R.drawable.history_page5 to "El decidido Nutripup: ¡Ahora quien podra ayudarnos!    Debemos combatir la suciedad con nutrición... ¡Así es, yo lo detendré!",
        R.drawable.history_page6 to "Trabajando en comunidad: Es bueno trabajar en comunidad. Y es necesaria una buena alimentación. ¡Acompañnme a salvar la ciudad! ",
        R.drawable.history_page7 to "Necesito tu ayuda para derrotarlo. Solo tú puedes ayudarme a derrotarlo ¡Debes escoger mi comida para obtener nutrientes! Debes leer el libro de superpoderes"
    )

    val comicPages = comicPagesWithText.map { it.first }
    val comicTexts = comicPagesWithText.map { it.second }

    var currentPage by remember { mutableStateOf(0) }
    var isTransitioning by remember { mutableStateOf(false) }

    // Text-to-Speech
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

    val scale by animateFloatAsState(
        targetValue = if (isTransitioning) 0.8f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "scale_animation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isTransitioning) 0f else 1f,
        animationSpec = tween(durationMillis = 300),
        finishedListener = {
            if (isTransitioning) {
                isTransitioning = false
            }
        },
        label = "alpha_animation"
    )

    fun nextPage() {
        if (currentPage < comicPages.size - 1) {
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

    fun readComicText() {
        tts?.speak(comicTexts[currentPage], TextToSpeech.QUEUE_FLUSH, null, null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF17AFE4))
    ) {
        Image(
            painter = painterResource(id = comicPages[currentPage]),
            contentDescription = "Comic page ${currentPage + 1}",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 65.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
        )

        // Botón de audio para leer el texto del comic
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)  // Empieza desde arriba-izquierda
                .offset(
                    x = 0.dp,  // O usa porcentaje con BoxWithConstraints
                    y = -150.dp
                )
                .padding(5.dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.9f))
                .clickable { readComicText() },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "🔊",
                fontSize = 30.sp,

            )
        }
    }

    // Botón Home
    Image(
        painter = painterResource(id = R.drawable.home_button),
        contentDescription = "Home Button",
        modifier = Modifier
            .size(110.dp)
            .offset(x = 140.dp, y = 755.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigateToHome() },
        contentScale = ContentScale.Fit
    )

    // Flecha izquierda
    Image(
        painter = painterResource(id = R.drawable.left_arrow),
        contentDescription = "Previous Page",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 50.dp, y = 785.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { previousPage() },
        contentScale = ContentScale.Fit,
        alpha = if (currentPage > 0) 1f else 0.3f
    )

    // Flecha derecha
    Image(
        painter = painterResource(id = R.drawable.right_arrow),
        contentDescription = "Next Page",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 285.dp, y = 785.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { nextPage() },
        contentScale = ContentScale.Fit,
        alpha = if (currentPage < comicPages.size - 1) 1f else 0.3f
    )
}

@Preview(showBackground = true)
@Composable
fun ComicHistoryPreview() {
    SuperFruitsTheme {
        ComicHistory { }
    }
}