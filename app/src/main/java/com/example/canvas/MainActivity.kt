package com.example.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.canvas.ui.theme.CanvasTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        ArcsWithColors()
                    }

                }
            }
        }
    }
}

@Composable
fun DrawLines() {
    val rotationAngleState = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        val animationDuration = 10000L // 10 seconds
        val startTime = System.currentTimeMillis()

        while (true) {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - startTime

            val progress = elapsedTime.toFloat() / animationDuration
            val rotationSpeed = 10f // Increase or decrease this value to adjust the rotation speed
            val easedProgress = 1 - (1 - progress) * (1 - progress) // Decelerating easing
            val rotationAngle = (360f * rotationSpeed * easedProgress) % 360f

            rotationAngleState.value = rotationAngle

            if (progress >= 1f) {
                break // Exit the loop after the animation duration is reached
            }

            delay(10) // Adjust the delay time as needed for the desired frame rate
        }
    }


    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Gray,
        Color.Black
    )


    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        val rotationAngle = rotationAngleState.value
        val center = Offset(size.width / 2, size.height / 2)
        val radius = minOf(size.width, size.height) / 2
        val random = Random(0)

        for (angle in 15 until 360 step 30) {
            val rotatedAngle = (angle + rotationAngle) % 360
            val radians = Math.toRadians(rotatedAngle.toDouble())
            val startX = center.x + (radius * cos(radians)).toFloat()
            val startY = center.y + (radius * sin(radians)).toFloat()
            val endX = center.x + ((radius-50) * cos(radians)).toFloat()
            val endY = center.y + ((radius-50) * sin(radians)).toFloat()
            val randomColor = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            drawLine(color = randomColor, start = Offset(startX, startY), end = Offset(endX, endY), strokeWidth = 15f)
        }
    }
}

@Composable
fun ArcsWithColors() {
    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta)
    val startAngle = 15f
    val sweepAngle = 30f

    Canvas(modifier = Modifier.size(300.dp)) {
        for (i in 0 until 12) {
            val color = colors[i % colors.size]
            drawArc(
                color = color,
                startAngle = startAngle + i * sweepAngle,
                sweepAngle = sweepAngle,
                useCenter = true,

            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvasTheme {
        Greeting("Android")
    }
}