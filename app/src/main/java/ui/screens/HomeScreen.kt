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
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToStory: () -> Unit = {},
    onNavigateToHelp: () -> Unit = {},
    onNavigateToBattle: () -> Unit = {},
    onNavigateToManual: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.background_home),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.superfruits_logo),
            contentDescription = "SuperFruits Logo",
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(12.dp))
                .offset(x = -50.dp, y = -50.dp),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                GameMenuCard(
                    title = "Historia de\nNutriPup",
                    imageRes = R.drawable.comic_history,
                    onClick = onNavigateToStory,
                )

                GameMenuCard(
                    title = "Ayuda a\nNutripup",
                    imageRes = R.drawable.nutripup_home,
                    backgroundColor = Color(0xFFF0F8FB),
                    onClick = onNavigateToHelp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                GameMenuCard(
                    title = "La gran\nbatalla",
                    imageRes = R.drawable.boom,
                    onClick = onNavigateToBattle,
                )

                GameMenuCard(
                    title = "Manual de\nsuperpoderes",
                    imageRes = R.drawable.nutripup_story,
                    onClick = onNavigateToManual,
                )
            }
        }
    }
}

@Composable
fun GameMenuCard(
    title: String,
    imageRes: Int,
    backgroundColor: Color = Color(0xFFF0F8FB),
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                modifier = Modifier.offset(y = (-20).dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SuperFruitsTheme {
        HomeScreen()
    }
}