package com.example.canvas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvas.model.LuckyItem
import com.example.canvas.ui.LuckyWheel
import com.example.canvas.ui.theme.CanvasTheme
import com.example.canvas.ui.theme.CustomCanvasAnimation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    StepOne()
                }
            }
        }
    }
}


@Composable
fun StepOne() {

    val context = LocalContext.current
    val myList = remember {
        mutableStateListOf(
            LuckyItem(
                name = "Settings",
                icon = R.drawable.baseline_build_24,
                color = Color(0xFF3b322b)
            ),
            LuckyItem(
                name = "Tick",
                icon = R.drawable.baseline_android_24,
                color = Color(0xFF8a61b1)
            ),
            LuckyItem(
                name = "Contactless",
                icon = R.drawable.baseline_contactless_24,
                color = Color(0xFF3b322b)
            ),
            LuckyItem(
                name = "Tick",
                icon = R.drawable.baseline_android_24,
                color = Color(0xFF8a61b1)
            ),
            LuckyItem(
                name = "Bunny",
                icon = R.drawable.baseline_cruelty_free_24,
                color = Color(0xFF3b322b)
            ),
            LuckyItem(
                name = "Tick",
                icon = R.drawable.baseline_android_24,
                color = Color(0xFF8a61b1)
            ),
            LuckyItem(
                name = "Contactless",
                icon = R.drawable.baseline_contactless_24,
                color = Color(0xFF3b322b)
            ),
            LuckyItem(
                name = "Tick",
                icon = R.drawable.baseline_android_24,
                color = Color(0xFF8a61b1)
            ),
            LuckyItem(
                name = "Tick",
                icon = R.drawable.baseline_android_24,
                color = Color(0xFF3b322b)
            ),
            LuckyItem(
                name = "Bunny",
                icon = R.drawable.baseline_cruelty_free_24,
                color = Color(0xFF8a61b1)

            ),
            LuckyItem(
                name = "Contactless",
                icon = R.drawable.baseline_contactless_24,
                color = Color(0xFF3b322b)
            ),
            LuckyItem(
                name = "Tick",
                icon = R.drawable.baseline_android_24,
                color = Color(0xFF8a61b1)
            ),
        )
    }
    var isRotationStarted by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        LuckyWheel(
            modifier = Modifier
                .size(348.dp)
                .align(alignment = Alignment.Center),
            isRotationStarted = isRotationStarted,
            items = myList
        ) {
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        }



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

            Text(
                text = "Spin",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun StepOnePreview() {
    CanvasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
          StepOne()
        }
    }
}


