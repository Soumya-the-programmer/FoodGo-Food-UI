package com.example.foodui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// lobster font defining
val lobsterFont = FontFamily(
    Font(R.font.lobster_regular)
)

// function to create splash screen
@Composable
fun SplashScreen()
{
    // ui screen
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFee2a38).copy(alpha = 0.75f),
                Color(0xFFee2a38)
            ),
            startY = 0.1f
        )
        )
    )
    {
        // for the title box
        Box(modifier = Modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        )
        {
            // for the title
            Text(
                text = "FoodGo",
                style = TextStyle(
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = lobsterFont,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // for the bottom image
        Image(painter = painterResource(id = R.drawable.burger_foodai),
            contentDescription = "BurgerFoodGo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(240.dp).width(320.dp).align(Alignment.BottomStart)
        )
    }
}