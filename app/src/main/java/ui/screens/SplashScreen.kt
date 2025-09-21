package com.example.superfruits.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfruits.R
import com.example.superfruits.ui.theme.SuperFruitsTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToHome()
    }

    val radialGradient = Brush.radialGradient(
        colors = listOf(
            Color(0xFFF1F130),
            Color(0xFFF28E43),
            Color(0xFFF54D52)
        ),
        center = Offset.Unspecified,
        radius = 700f
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = radialGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.superfruits_logo),
                contentDescription = "SuperFruits Logo",
                modifier = Modifier
                    .size(400.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .offset(x = -50.dp, y = 50.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¡Alimenta tus poderes, salva el mundo!",
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = (-120).dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SuperFruitsTheme {
        SplashScreen { }
    }
}