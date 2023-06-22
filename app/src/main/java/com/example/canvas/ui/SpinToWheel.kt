package com.example.canvas.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.canvas.R
import com.example.canvas.model.LuckyItem
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun LuckyWheel(
    modifier: Modifier = Modifier,
    isRotationStarted:Boolean,
    items:List<LuckyItem>,
    animationDuration:Long=10000L,
    target: Int=Random.nextInt(items.size),
    smoothStopSpeed:Int=5,   // Increase or decrease this value to adjust the smooth stop rotation speed
    rotationSpeed:Float=50f, // Increase or decrease this value to adjust the rotation speed
    onFinish:(LuckyItem)->Unit
) {

    val context = LocalContext.current
    var startAngle = 0f
    val tmpAngle = 0f
    val sweepAngle = 360f / items.size
    val sweepHalfAngle = (360f / items.size) / 2
    val rotationAngleState = remember { mutableStateOf(0f) }


    LaunchedEffect(isRotationStarted) {
        //val randomNumber = Random.nextInt(colors.size)
        if (isRotationStarted) {
            val startTime = System.currentTimeMillis()

            while (true) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime

                val progress = elapsedTime.toFloat() / animationDuration
                val easedProgress = calculateSmoothStop(progress = progress, repetitions = smoothStopSpeed)
                val rotationAngle =
                    ((360f * rotationSpeed * easedProgress) % 360f) + 270 - (target * sweepAngle) - sweepHalfAngle

                rotationAngleState.value = rotationAngle.toFloat()

                if (progress >= 1f) {
                    onFinish(items[target])
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

        Canvas(
            modifier = Modifier
                .fillMaxSize().padding(10.dp)
        ) {
            rotate(rotationAngleState.value) {
                items.forEachIndexed { index, item ->
                    val arcAngle = tmpAngle + (index * sweepAngle)
                    drawArc(
                        color = item.color,
                        startAngle = arcAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                    )

                    if (startAngle + sweepAngle > 360) {
                        startAngle = 0f
                    }
                    startAngle += sweepAngle

                    drawIntoCanvas {
                        drawImageInCanvas(
                            canvas = it,
                            tmpAngle = arcAngle,
                            bitmap = getBitmapFromVectorDrawable(
                                context,
                                item.icon
                            ).rotateBitmap(startAngle + 90 - sweepHalfAngle),
                            mRadius = size.width.toInt(),
                            mCenter = size.width.toInt() / 2,
                            listSize = items.size
                        )
                    }


                }
            }
        }


        Image(
            painterResource(id = R.drawable.red_arrow_down),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)

        )


    }
}