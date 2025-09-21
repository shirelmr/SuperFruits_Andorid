package com.example.superfruits.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfruits.R
import com.example.superfruits.ui.theme.SuperFruitsTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onNavigateToStory: () -> Unit = {},
    onNavigateToHelp: () -> Unit = {},
    onNavigateToBattle: () -> Unit = {},
    onNavigateToManual: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}

) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.game_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.flappypup_logo),
            contentDescription = "Flappy puppy Logo",
            modifier = Modifier
                .size(600.dp)
                .clip(RoundedCornerShape(12.dp))
                .offset(x = 0.dp, y = -170.dp),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.home_button),
            contentDescription = "Home Button",
            modifier = Modifier
                .size(100.dp)
                .offset(x = 70.dp, y = 670.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onNavigateToHome },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.play_button),
            contentDescription = "Play Button",
            modifier = Modifier
                .size(100.dp)
                .offset(x = 220.dp, y = 670.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onNavigateToHome },
            contentScale = ContentScale.Fit
        )

        }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    SuperFruitsTheme {
        GameScreen()
    }
}