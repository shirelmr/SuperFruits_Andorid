package com.example.superfruits.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfruits.R
import com.example.superfruits.ui.theme.SuperFruitsTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

data class SimplePuppyStats(
    val strength: Int = 50,
    val speed: Int = 50,
    val intelligence: Int = 50,
    val health: Int = 50
)

data class FoodCarouselItem(
    val id: String,
    val name: String,
    val imageRes: Int,
    val isHealthy: Boolean
)

@Composable
fun FoodSelection(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {}
) {
    var puppyStats by remember { mutableStateOf(SimplePuppyStats()) }

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

    var selectedFoodName by remember { mutableStateOf<String?>(null) }
    var puppyPosition by remember { mutableStateOf(Offset.Zero) }
    var puppySize by remember { mutableStateOf(IntSize.Zero) }

    // Estado para controlar si el perrito tiene la boca abierta
    var isPuppyMouthOpen by remember { mutableStateOf(false) }

    val foods = remember {
        listOf(
            FoodCarouselItem("apple", "Manzana", R.drawable.apple, true),
            FoodCarouselItem("banana", "Plátano", R.drawable.banana, true),
            FoodCarouselItem("orange", "Naranja", R.drawable.orange, true),
            FoodCarouselItem("strawberry", "Fresa", R.drawable.strawberry, true),
            FoodCarouselItem("kiwi", "Kiwi", R.drawable.kiwi, true),
            FoodCarouselItem("blueberry", "Mora azul", R.drawable.blueberry, true),
            FoodCarouselItem("pineapple", "Piña", R.drawable.pineapple, true),
            FoodCarouselItem("watermelon", "Sandía", R.drawable.watermelon, true),

            FoodCarouselItem("broccoli", "Brócoli", R.drawable.broccoli, true),
            FoodCarouselItem("carrot", "Zanahoria", R.drawable.carrot, true),
            FoodCarouselItem("spinach", "Espinaca", R.drawable.spinach, true),
            FoodCarouselItem("tomato", "Tomate", R.drawable.tomato, true),
            FoodCarouselItem("corn", "Elote", R.drawable.corn, true),
            FoodCarouselItem("celery", "Apio", R.drawable.celery, true),

            FoodCarouselItem("milk", "Leche", R.drawable.milk, true),
            FoodCarouselItem("tuna", "Atún", R.drawable.tuna, true),
            FoodCarouselItem("chicken", "Pollo", R.drawable.chicken, true),
            FoodCarouselItem("egg", "Huevo", R.drawable.egg, true),
            FoodCarouselItem("fish", "Pescado", R.drawable.fish, true),

            FoodCarouselItem("yogurt", "Yogurt", R.drawable.yogurt, true),
            FoodCarouselItem("cheese", "Queso", R.drawable.cheese, true),

            FoodCarouselItem("almonds", "Almendras", R.drawable.almonds, true),
            FoodCarouselItem("avocado", "Aguacate", R.drawable.avocado, true),
            FoodCarouselItem("rice", "Arroz", R.drawable.rice, true),
            FoodCarouselItem("potato", "Papa", R.drawable.potato, true),
            FoodCarouselItem("tortillas", "Tortillas", R.drawable.tortillas, true),
            FoodCarouselItem("beans", "Frijoles", R.drawable.beans, true),
            FoodCarouselItem("chickpeas", "Garbanzos", R.drawable.chickpeas, true),
            FoodCarouselItem("oats", "Avena", R.drawable.oats, true),

            FoodCarouselItem("fries", "Papas Fritas", R.drawable.fries, false),
            FoodCarouselItem("burger", "Hamburguesa", R.drawable.burger, false),
            FoodCarouselItem("pizza", "Pizza", R.drawable.pizza, false),
            FoodCarouselItem("hotdog", "Hot Dog", R.drawable.hotdog, false),
            FoodCarouselItem("soda", "Refresco", R.drawable.soda, false),
            FoodCarouselItem("candy", "Dulces", R.drawable.candy, false),
            FoodCarouselItem("chips", "Papitas", R.drawable.chips, false),
            FoodCarouselItem("donut", "Dona", R.drawable.donut, false),
            FoodCarouselItem("ice_cream", "Helado", R.drawable.ice_cream, false),
            FoodCarouselItem("cookies", "Galletas", R.drawable.cookies, false),
            FoodCarouselItem("cake", "Pastel", R.drawable.cake, false),
            FoodCarouselItem("chocolate", "Chocolate", R.drawable.chocolate, false),
            FoodCarouselItem("nachos", "Nachos", R.drawable.nachos, false),
            FoodCarouselItem("nuggets", "Nuggets", R.drawable.nuggets, false),
            FoodCarouselItem("milkshake", "Malteada", R.drawable.milkshake, false),
            FoodCarouselItem("churros", "Churros", R.drawable.churros, false),
            FoodCarouselItem("cotton_candy", "Algodón de Azúcar", R.drawable.cotton_candy, false),
            FoodCarouselItem("gummy_bears", "Ositos de Gomita", R.drawable.gummy_bears, false),
            FoodCarouselItem("lollipop", "Paleta", R.drawable.lollipop, false),
            FoodCarouselItem("cupcake", "Cupcake", R.drawable.cupcake, false),
            FoodCarouselItem("brownie", "Brownie", R.drawable.brownie, false),
            FoodCarouselItem("muffin", "Muffin", R.drawable.muffin, false),
        ).shuffled()
    }

    var carouselStartIndex by remember { mutableStateOf(0) }
    var isTransitioning by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isTransitioning) 0f else 1f,
        animationSpec = tween(durationMillis = 250),
        finishedListener = { if (isTransitioning) isTransitioning = false },
        label = "carousel_fade"
    )

    fun nextFoods() {
        if (carouselStartIndex < foods.size - 3) {
            selectedFoodName = null
            isTransitioning = true
            carouselStartIndex++
        }
    }

    fun previousFoods() {
        if (carouselStartIndex > 0) {
            selectedFoodName = null
            isTransitioning = true
            carouselStartIndex--
        }
    }

    fun feedFood(isHealthy: Boolean, foodName: String) {
        puppyStats = if (isHealthy) {
            SimplePuppyStats(
                strength = (puppyStats.strength + 5).coerceAtMost(100),
                speed = (puppyStats.speed + 3).coerceAtMost(100),
                intelligence = (puppyStats.intelligence + 5).coerceAtMost(100),
                health = (puppyStats.health + 10).coerceAtMost(100)
            )
        } else {
            SimplePuppyStats(
                strength = (puppyStats.strength - 3).coerceAtLeast(0),
                speed = (puppyStats.speed - 3).coerceAtLeast(0),
                intelligence = (puppyStats.intelligence - 2).coerceAtLeast(0),
                health = (puppyStats.health - 8).coerceAtLeast(0)
            )
        }


        // Mantener la boca abierta por un momento más después de comer
        isPuppyMouthOpen = true
        GlobalScope.launch {
            delay(800) // Espera 800ms antes de cerrar la boca
            isPuppyMouthOpen = false
            delay(1200)
            selectedFoodName = null
        }
    }

    fun speakFoodName(foodName: String) {
        tts?.speak(foodName, TextToSpeech.QUEUE_FLUSH, null, null)
        selectedFoodName = foodName

        GlobalScope.launch {
            delay(1500)
            selectedFoodName = null
        }
    }

    // Función para verificar si la comida está cerca del perrito
    fun checkIfNearPuppy(foodPosition: Offset): Boolean {
        return foodPosition.x >= puppyPosition.x &&
                foodPosition.x <= puppyPosition.x + puppySize.width &&
                foodPosition.y >= puppyPosition.y &&
                foodPosition.y <= puppyPosition.y + puppySize.height
    }

    val currentFoods = remember(carouselStartIndex) {
        foods.subList(
            carouselStartIndex,
            (carouselStartIndex + 3).coerceAtMost(foods.size)
        )
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_kitchen),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    puppyPosition = Offset(
                        coordinates.size.width / 4f,
                        coordinates.size.height / 4f
                    )
                    puppySize = IntSize(
                        coordinates.size.width / 2,
                        coordinates.size.height / 2
                    )
                }
        ) {
            // Cambiar imagen del perrito según el estado de la boca
            Image(
                painter = painterResource(
                    id = if (isPuppyMouthOpen) R.drawable.nutripup1 else R.drawable.nutripup
                ),
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Image(
            painter = painterResource(id = R.drawable.table),
            contentDescription = "Table",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.food),
            contentDescription = "Plates",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
        ) {
            key(currentFoods.getOrNull(0)?.id) {
                if (currentFoods.isNotEmpty()) {
                    DraggableFood(
                        food = currentFoods[0],
                        alpha = alpha,
                        offsetX = 55.dp,
                        offsetY = (-210).dp,
                        alignment = Alignment.BottomStart,
                        puppyPosition = puppyPosition,
                        puppySize = puppySize,
                        onFeedPuppy = {
                            feedFood(currentFoods[0].isHealthy, currentFoods[0].name)
                        },
                        onClick = {
                            speakFoodName(currentFoods[0].name)
                        },
                        onDragNearPuppy = { isNear ->
                            isPuppyMouthOpen = isNear
                        }
                    )
                }
            }

            key(currentFoods.getOrNull(1)?.id) {
                if (currentFoods.size > 1) {
                    DraggableFood(
                        food = currentFoods[1],
                        alpha = alpha,
                        offsetX = 5.dp,
                        offsetY = (-205).dp,
                        alignment = Alignment.BottomCenter,
                        puppyPosition = puppyPosition,
                        puppySize = puppySize,
                        onFeedPuppy = {
                            feedFood(currentFoods[1].isHealthy, currentFoods[1].name)
                        },
                        onClick = {
                            speakFoodName(currentFoods[1].name)
                        },
                        onDragNearPuppy = { isNear ->
                            isPuppyMouthOpen = isNear
                        }
                    )
                }
            }

            key(currentFoods.getOrNull(2)?.id) {
                if (currentFoods.size > 2) {
                    DraggableFood(
                        food = currentFoods[2],
                        alpha = alpha,
                        offsetX = (-35).dp,
                        offsetY = (-205).dp,
                        alignment = Alignment.BottomEnd,
                        puppyPosition = puppyPosition,
                        puppySize = puppySize,
                        onFeedPuppy = {
                            feedFood(currentFoods[2].isHealthy, currentFoods[2].name)
                        },
                        onClick = {
                            speakFoodName(currentFoods[2].name)
                        },
                        onDragNearPuppy = { isNear ->
                            isPuppyMouthOpen = isNear
                        }
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "Previous Foods",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = 30.dp, y = (-120).dp)
                    .clip(CircleShape)
                    .clickable { previousFoods() },
                contentScale = ContentScale.Fit,
                alpha = if (carouselStartIndex > 0) 1f else 0.3f
            )

            Image(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = "Next Foods",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-30).dp, y = (-120).dp)
                    .clip(CircleShape)
                    .clickable { nextFoods() },
                contentScale = ContentScale.Fit,
                alpha = if (carouselStartIndex < foods.size - 3) 1f else 0.3f
            )
        }

        selectedFoodName?.let { name ->
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-100).dp)
                    .background(
                        Color.White.copy(alpha = 0.95f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17AFE4)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .background(
                    Color.White.copy(alpha = 0.9f),
                    RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SuperPowerBar("💪", "Fuerza", puppyStats.strength, Color(0xFFE74C3C))
            SuperPowerBar("⚡", "Velocidad", puppyStats.speed, Color(0xFFF39C12))
            SuperPowerBar("🧠", "Inteligencia", puppyStats.intelligence, Color(0xFF3498DB))
            SuperPowerBar("❤️", "Salud", puppyStats.health, Color(0xFF2ECC71))
        }

        Image(
            painter = painterResource(id = R.drawable.home_button),
            contentDescription = "Home Button",
            modifier = Modifier
                .size(110.dp)
                .offset(x = 140.dp, y = 740.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onNavigateToHome() },
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DraggableFood(
    food: FoodCarouselItem,
    alpha: Float,
    offsetX: androidx.compose.ui.unit.Dp,
    offsetY: androidx.compose.ui.unit.Dp,
    alignment: Alignment,
    puppyPosition: Offset,
    puppySize: IntSize,
    onFeedPuppy: () -> Unit,
    onClick: () -> Unit,
    onDragNearPuppy: (Boolean) -> Unit
) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }
    var itemPosition by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = food.imageRes),
            contentDescription = food.name,
            modifier = Modifier
                .size(80.dp)
                .align(alignment)
                .offset(x = offsetX, y = offsetY)
                .onGloballyPositioned { coordinates ->
                    itemPosition = coordinates.positionInRoot()
                }
                .graphicsLayer {
                    translationX = dragOffset.x
                    translationY = dragOffset.y
                    scaleX = if (isDragging) 1.3f else 1f
                    scaleY = if (isDragging) 1.3f else 1f
                }
                .pointerInput(food.id) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragOffset += dragAmount

                            // Verificar si está cerca del perrito mientras arrastra
                            val currentPosition = Offset(
                                itemPosition.x + dragOffset.x + 40f,
                                itemPosition.y + dragOffset.y + 40f
                            )

                            val isNearPuppy = currentPosition.x >= puppyPosition.x &&
                                    currentPosition.x <= puppyPosition.x + puppySize.width &&
                                    currentPosition.y >= puppyPosition.y &&
                                    currentPosition.y <= puppyPosition.y + puppySize.height

                            onDragNearPuppy(isNearPuppy)
                        },
                        onDragEnd = {
                            val finalPosition = Offset(
                                itemPosition.x + dragOffset.x + 40f,
                                itemPosition.y + dragOffset.y + 40f
                            )

                            val isOverPuppy = finalPosition.x >= puppyPosition.x &&
                                    finalPosition.x <= puppyPosition.x + puppySize.width &&
                                    finalPosition.y >= puppyPosition.y &&
                                    finalPosition.y <= puppyPosition.y + puppySize.height

                            if (isOverPuppy) {
                                onFeedPuppy()
                            } else {
                                onDragNearPuppy(false)
                            }

                            dragOffset = Offset.Zero
                            isDragging = false
                        }
                    )
                }
                .clickable { onClick() },
            contentScale = ContentScale.Fit,
            alpha = alpha
        )
    }
}

@Composable
fun SuperPowerBar(
    icon: String,
    label: String,
    value: Int,
    color: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth()  // ⬅️ Cambié de width(120.dp) a fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween  // ⬅️ Agregué esto
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = icon,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "$value",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.LightGray, RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(value / 100f)
                    .background(color, RoundedCornerShape(10.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodSelectionPreview() {
    SuperFruitsTheme {
        FoodSelection()
    }
}