package com.example.superfruits.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6EFC4))
    ) {

        Image(
            painter = painterResource(id = R.drawable.manual),
            contentDescription = "Comic",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 65.dp)
        )
    }

    Image(
        painter = painterResource(id = R.drawable.home_button),
        contentDescription = "Home Button",
        modifier = Modifier
            .size(110.dp)
            .offset(x = 140.dp, y = 700.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigateToHome },
        contentScale = ContentScale.Fit
    )

    Image(
        painter = painterResource(id = R.drawable.left_arrow),
        contentDescription = "Home Button",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 50.dp, y = 730.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigateToHome },
        contentScale = ContentScale.Fit
    )

    Image(
        painter = painterResource(id = R.drawable.right_arrow),
        contentDescription = "Home Button",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 285.dp, y = 730.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigateToHome },
        contentScale = ContentScale.Fit
    )

}


@Preview(showBackground = true)
@Composable
fun ManualScreenPreview() {
    SuperFruitsTheme {
        ManualScreen() { }
    }
}