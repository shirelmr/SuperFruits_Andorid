package com.example.superfruits.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfruits.R
import com.example.superfruits.ui.theme.SuperFruitsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random
import androidx.compose.foundation.Canvas

data class Obstacle(
    var x: Float,
    val gapY: Float,
    val gapHeight: Float = 500f,
    val width: Float = 100f,
    var scored: Boolean = false
)

@Composable
fun FlappyBirdGame(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit
) {
    var gameStarted by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var screenSize by remember { mutableStateOf(IntSize.Zero) }

    var backgroundOffset by remember { mutableStateOf(0f) }

    var birdY by remember { mutableStateOf(0f) }
    var birdVelocity by remember { mutableStateOf(0f) }

    var obstacles by remember { mutableStateOf(listOf<Obstacle>()) }

    val gravity = 1.2f
    val jumpStrength = -20f
    val birdSize = 70f
    val birdX = 100f
    val density = LocalDensity.current

    val collisionMargin = 15f

    LaunchedEffect(screenSize) {
        if (screenSize.height > 0 && birdY == 0f) {
            birdY = screenSize.height / 2f
        }
    }

    fun checkCollision(): Boolean {
        if (screenSize.height == 0) return false

        val birdRect = Rect(
            left = birdX + collisionMargin,
            top = birdY + collisionMargin,
            right = birdX + birdSize - collisionMargin,
            bottom = birdY + birdSize - collisionMargin
        )

        if (birdRect.top <= 0 || birdRect.bottom >= screenSize.height) {
            return true
        }

        obstacles.forEach { obstacle ->
            if (birdRect.right > obstacle.x && birdRect.left < obstacle.x + obstacle.width) {
                if (birdRect.top < obstacle.gapY || birdRect.bottom > obstacle.gapY + obstacle.gapHeight) {
                    return true
                }
            }
        }

        return false
    }

    LaunchedEffect(gameStarted, gameOver, screenSize) {
        if (gameStarted && !gameOver && screenSize.width > 0) {
            while (isActive && !gameOver) {
                delay(16) // ~60 FPS

                backgroundOffset -= 2f
                if (backgroundOffset <= -screenSize.width) {
                    backgroundOffset = 0f
                }

                birdVelocity += gravity
                birdY += birdVelocity

                obstacles = obstacles.map { obstacle ->
                    obstacle.copy(x = obstacle.x - 5f)
                }.filter { it.x > -it.width }

                if (obstacles.isEmpty() || obstacles.last().x < screenSize.width - 800f) {
                    val gapY = Random.nextFloat() * (screenSize.height - 800f) + 300f
                    obstacles = obstacles + Obstacle(
                        x = screenSize.width.toFloat(),
                        gapY = gapY,
                        scored = false
                    )
                }

                obstacles = obstacles.map { obstacle ->
                    if (!obstacle.scored && obstacle.x + obstacle.width < birdX) {
                        score++
                        obstacle.copy(scored = true)
                    } else {
                        obstacle
                    }
                }

                if (checkCollision()) {
                    gameOver = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { screenSize = it }
            .clickable(enabled = !gameOver) {
                if (!gameStarted) {
                    gameStarted = true
                } else {
                    birdVelocity = jumpStrength
                }
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.flappybird_background),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = with(density) { backgroundOffset.toDp() })
            )
            Image(
                painter = painterResource(id = R.drawable.flappybird_background),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = with(density) { (backgroundOffset + screenSize.width).toDp() })
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            obstacles.forEach { obstacle ->
                drawRect(
                    color = Color(0xFF4CAF50),
                    topLeft = Offset(obstacle.x, 0f),
                    size = Size(obstacle.width, obstacle.gapY)
                )
                drawRect(
                    color = Color(0xFF2E7D32),
                    topLeft = Offset(obstacle.x - 15f, obstacle.gapY - 40f),
                    size = Size(obstacle.width + 30f, 40f)
                )

                drawRect(
                    color = Color(0xFF4CAF50),
                    topLeft = Offset(obstacle.x, obstacle.gapY + obstacle.gapHeight),
                    size = Size(obstacle.width, size.height - (obstacle.gapY + obstacle.gapHeight))
                )
                drawRect(
                    color = Color(0xFF2E7D32),
                    topLeft = Offset(obstacle.x - 15f, obstacle.gapY + obstacle.gapHeight),
                    size = Size(obstacle.width + 30f, 40f)
                )
            }
        }

        if (screenSize.width > 0) {
            Image(
                painter = painterResource(id = R.drawable.nutripup_home),
                contentDescription = "Bird",
                modifier = Modifier
                    .size(birdSize.dp)
                    .offset(
                        x = with(density) { birdX.toDp() },
                        y = with(density) { birdY.toDp() }
                    ),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "Score: $score",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
        )

        if (!gameStarted && !gameOver) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(15.dp))
                    .padding(40.dp)
            ) {
                Text(
                    text = "Toca para empezar",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17AFE4)
                )
            }
        }

        if (gameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Juego Terminado!",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Puntaje: $score",
                        fontSize = 32.sp,
                        color = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF4CAF50))
                            .clickable {
                                gameOver = false
                                gameStarted = false
                                score = 0
                                birdY = screenSize.height / 2f
                                birdVelocity = 0f
                                obstacles = listOf()
                                backgroundOffset = 0f
                            }
                            .padding(horizontal = 48.dp, vertical = 20.dp)
                    ) {
                        Text(
                            text = "Reintentar",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        if (!gameStarted || gameOver) {
            Image(
                painter = painterResource(id = R.drawable.home_button),
                contentDescription = "Home Button",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomCenter) // Mejor centrado
                    .offset(y = (-50).dp) // Un poco arriba del borde
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onNavigateToHome() },
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlappyBirdGamePreview() {
    SuperFruitsTheme {
        FlappyBirdGame { }
    }
}