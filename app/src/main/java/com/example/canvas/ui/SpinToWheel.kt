package com.example.canvas.ui

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
    isRotationStarted:Boolean,
    items:List<LuckyItem>,
    mRoundOfNumber:Int=4,
    target: Int=Random.nextInt(items.size),
    onFinish:(LuckyItem)->Unit
) {

    val context = LocalContext.current
    var startAngle = remember {
        0f
    }
    val tmpAngle = remember {
        0f
    }
    val sweepAngle = remember {
        360f / items.size
    }
    val sweepHalfAngle = remember {
        (360f / items.size) / 2
    }
    val rotationAngleState = remember { Animatable(0f) }

    val bitmapList = remember {
        items.map { getBitmapFromVectorDrawable(context,it.icon)}
    }



    LaunchedEffect(isRotationStarted) {
        if (isRotationStarted) {
            launch {
                val asd= (360f * mRoundOfNumber) + 270f - (sweepAngle*target) - 360f / items.size / 2
                rotationAngleState.animateTo(asd, animationSpec = tween(durationMillis = (mRoundOfNumber * 1050 + 900L).toInt()))
                onFinish(items[target])
            }
        } else {
            rotationAngleState.snapTo(0f)
        }
    }

    Box(modifier = modifier) {
        //Image(modifier=Modifier.align(Alignment.Center),imageVector = ImageVector.vectorResource(id = R.drawable.ic_body), contentDescription =null )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
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
                            bitmap = bitmapList[index].rotateBitmap(startAngle + 90 - sweepHalfAngle),
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