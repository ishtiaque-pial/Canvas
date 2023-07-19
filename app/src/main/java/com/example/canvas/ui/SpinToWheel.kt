package com.example.canvas.ui

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun LuckyWheel(
    modifier: Modifier = Modifier,
    isRotationStarted:()->Boolean,
    items:List<LuckyItem>,
    mRoundOfNumber:Int=10,
    target: Int=Random.nextInt(items.size),
    onFinish:(LuckyItem)->Unit
) {

    val context = LocalContext.current
    val startAngle = remember {
        mutableStateOf(0f)
    }
    val tmpAngle = remember {
        mutableStateOf(0f)
    }
    val sweepAngle = remember {
        mutableStateOf(360f / items.size)

    }
    val sweepHalfAngle = remember {
        mutableStateOf((360f / items.size) / 2)

    }
    val rotationAngleState = remember { Animatable(0f) }

    val bitmapList = remember(items) {
        items.map { getBitmapFromVectorDrawable(context,it.icon)}
    }



    LaunchedEffect(isRotationStarted.invoke()) {
        if (isRotationStarted.invoke()) {
            launch {
                val asd= (360f * mRoundOfNumber) + 270f - (sweepAngle.value*target) - 360f / items.size / 2
                rotationAngleState.animateTo(asd, animationSpec = tween(durationMillis = (mRoundOfNumber * 1050 + 900L).toInt()))
                onFinish(items[target])
            }
        } else {
            rotationAngleState.snapTo(0f)
        }
    }

    Box(modifier = modifier) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            rotate(rotationAngleState.value) {

                items.forEachIndexed { index, item ->
                    val arcAngle = tmpAngle.value + (index * sweepAngle.value)
                    drawArc(
                        color = item.color,
                        startAngle = arcAngle,
                        sweepAngle = sweepAngle.value,
                        useCenter = true,
                    )

                    if (startAngle.value + sweepAngle.value > 360) {
                        startAngle.value = 0f
                    }
                    startAngle.value += sweepAngle.value
                    drawIntoCanvas {
                        drawImageInCanvas(
                            canvas = it,
                            tmpAngle = arcAngle,
                            bitmap = bitmapList[index].rotateBitmap(startAngle.value + 90 - sweepHalfAngle.value),
                            mRadius = size.width.toInt(),
                            mCenter = size.width.toInt() / 2,
                            listSize = items.size
                        )
                    }


                }
            }
        }

        ImagePointer(modifier = Modifier.align(Alignment.TopCenter))

    }
}

@Composable
fun ImagePointer(modifier: Modifier){
    Image(
        painterResource(id = R.drawable.red_arrow_down),
        contentDescription = null,
        modifier =modifier

    )
}