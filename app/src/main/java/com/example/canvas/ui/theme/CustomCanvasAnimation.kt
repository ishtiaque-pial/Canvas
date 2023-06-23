package com.example.canvas.ui.theme


import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CustomCanvasAnimation() {
    val angle = remember {
        Animatable(0f)
    }

    val rotationAngleStart = remember { mutableStateOf(false) }
    LaunchedEffect(rotationAngleStart.value) {
        if (rotationAngleStart.value) {
            launch {
                //angle.snapTo(270f)
                angle.animateTo(360f * 10, animationSpec = tween(durationMillis = 5000))

            }
        } else {
            angle.snapTo(0f)
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .size(200.dp)
            .align(Alignment.Center)
            .clickable {
                rotationAngleStart.value = !rotationAngleStart.value
            },
            onDraw = {
                rotate(angle.value) {
                    drawRoundRect(
                        color = Color.Blue,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
            }
        )
    }


}