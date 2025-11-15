package com.example.superfruits.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.superfruits.R
import com.example.superfruits.ui.theme.SuperFruitsTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp

@Composable
fun ManualScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit
) {
    val manualPages = listOf(
        R.drawable.manual,
        R.drawable.manual_page2
        // R.drawable.manual_page3,
        // etc.
    )

    var currentPage by remember { mutableStateOf(0) }
    var isTransitioning by remember { mutableStateOf(false) }

    // Animación de transparencia
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6EFC4))
    ) {
        Image(
            painter = painterResource(id = manualPages[currentPage]),
            contentDescription = "Manual page ${currentPage + 1}",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(),
            alpha = alpha
        )
    }

    // Botón Home
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

    // Flecha izquierda (página anterior)
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

    // Flecha derecha (página siguiente)
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