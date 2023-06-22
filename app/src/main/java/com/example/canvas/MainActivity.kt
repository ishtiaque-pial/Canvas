package com.example.canvas

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.canvas.ui.theme.CanvasTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
                        ArcsWithColors(
                            modifier = Modifier
                                .size(348.dp)
                                .align(alignment = Alignment.Center)
                        )
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


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val rotationAngle = rotationAngleState.value
        val center = Offset(size.width / 2, size.height / 2)
        val radius = minOf(size.width, size.height) / 2
        val random = Random(0)

        for (angle in 15 until 360 step 30) {
            val rotatedAngle = (angle + rotationAngle) % 360
            val radians = Math.toRadians(rotatedAngle.toDouble())
            val startX = center.x + (radius * cos(radians)).toFloat()
            val startY = center.y + (radius * sin(radians)).toFloat()
            val endX = center.x + ((radius - 50) * cos(radians)).toFloat()
            val endY = center.y + ((radius - 50) * sin(radians)).toFloat()
            val randomColor = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            drawLine(
                color = randomColor,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 15f
            )
        }
    }
}

@Composable
fun ArcsWithColors(modifier: Modifier) {
    val colors = listOf(
        Color.Black,
        Color.Blue,
        Color.Green,
        Color.Cyan,
        Color.Magenta,
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Cyan,
        Color.Magenta,
        Color.Red,
        Color.Blue
    )
    val startAngle = 270f
    val sweepAngle = 360f / colors.size
    val initSweepAngle = 360f / colors.size
    val sweepHalfAngle = (360f / colors.size) / 2
    var isRotationStarted by remember { mutableStateOf(false) }

    val rotationAngleState = remember { mutableStateOf(0f) }

    LaunchedEffect(isRotationStarted) {
        val randomNumber = Random.Default.nextInt(colors.size)
        if (isRotationStarted) {
            val animationDuration = 10000L // 10 seconds
            val startTime = System.currentTimeMillis()

            while (true) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime

                val progress = elapsedTime.toFloat() / animationDuration
                val rotationSpeed =
                    50f // Increase or decrease this value to adjust the rotation speed
                val easedProgress =
                    1 - (1 - progress) * (1 - progress) * (1 - progress) * (1 - progress) * (1 - progress) // Decelerating easing
                val rotationAngle =
                    ((360f * rotationSpeed * easedProgress) % 360f) - (randomNumber * initSweepAngle) + sweepHalfAngle

                rotationAngleState.value = rotationAngle

                if (progress >= 1f) {
                    break // Exit the loop after the animation duration is reached
                }

                delay(16) // Adjust the delay time as needed for the desired frame rate
            }
        } else {
            rotationAngleState.value = 0f
        }
    }

    Box(modifier = modifier) {
        //Image(modifier=Modifier.align(Alignment.Center),imageVector = ImageVector.vectorResource(id = R.drawable.ic_body), contentDescription =null )
        
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            rotate(rotationAngleState.value) {
                colors.forEachIndexed { index, color ->
                    drawArc(
                        color = color,
                        startAngle = startAngle + (index * sweepAngle),
                        sweepAngle = sweepAngle,
                        useCenter = true,
                    )
                }
            }
        }
        
        Image(painterResource(id = R.drawable.pointer) , contentDescription = null,modifier=Modifier.align(Alignment.TopCenter).padding(top = 2.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color = Color.Yellow)
                .align(alignment = Alignment.Center)
                .clickable {
                    isRotationStarted = !isRotationStarted
                }
        ) {

            Text(text = "Spin", fontSize = 18.sp, fontWeight = FontWeight.Bold,modifier = Modifier.align(alignment = Alignment.Center))
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

