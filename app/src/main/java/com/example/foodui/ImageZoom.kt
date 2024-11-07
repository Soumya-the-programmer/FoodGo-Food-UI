package com.example.foodui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// function for the main ImageZoom Content
@Composable
fun ImageZoom(
    url : String,
    heading : String,
    subHeading : String,
    price : Int,
    description : String,
    navController: NavController
)
{
    // calling the function for the whole app structure
    AppStructure(url = url,
        heading = heading,
        subHeading = subHeading,
        description = description,
        price = price,
        navController = navController
        )
}

// function for the activity structure
@Composable
fun AppStructure(url : String,
                 heading: String,
                 subHeading: String,
                 price: Int,
                 description: String,
                 navController: NavController
                 )
{
    // holding the snack bar host state
    val snackbarHostState = remember { SnackbarHostState() }

    // for showing snack bar
    Scaffold(
        // host state defining
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
    {
        // for the whole structure placing vertically
        Column(modifier = Modifier.fillMaxSize())
        {
            // calling upper box structure function
            UpperBoxStructure(url = url, navController = navController)

            // calling lower box structure function
            LowerBoxStructure(modifier = Modifier.fillMaxSize(),
                heading = heading,
                subHeading = subHeading,
                price = price,
                description = description,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

// function for the upper box structure including image and back button
@Composable
fun UpperBoxStructure(url : String, navController: NavController)
{
    // for the image and back button
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.45f)
        .background(color = Color.White)
        .padding(start = 20.dp, top = 50.dp)
    )
    {
        // placing the image
        AsyncImage(model = url,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
            )

        // placing the back button
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
            contentAlignment = Alignment.TopStart
        )
        {
            // for the back button
            Box(modifier = Modifier
                .size(30.dp)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        navController.navigate("HomeFeedMainContent")
                    }
                ),
                contentAlignment = Alignment.TopStart
            )
            {
                // back icon image
                Image(painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxSize()
                    )
            }
        }
    }
}

// function for the lower structure of ui
@Composable
fun LowerBoxStructure(
    modifier: Modifier = Modifier,
    heading: String,
    subHeading: String,
    price: Int,
    description: String,
    snackbarHostState: SnackbarHostState
)
{
    // for the headings and sub heading and description and quantity and button placing vertically
    Column(modifier = modifier.background(color = Color.White).padding(20.dp))
    {
        // calling the function for the sub heading, heading and description
        TitleSubTitleDescription(heading = heading,
            subHeading = subHeading,
            description = description)

        // for placing at bottom
        Column(modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Bottom)
        {
            // for the quantity and shop now button
            QuantityAndShopButton(price = price, snackbarHostState = snackbarHostState)
        }
    }
}

// function for the quantity and shop button
@Composable
fun QuantityAndShopButton(price: Int, snackbarHostState: SnackbarHostState)
{
    // holding the Coroutine scope
    val scope = rememberCoroutineScope()

    // storing quantity in state
    var quantity by remember { mutableIntStateOf(1) }

    // for the adding quantity and button
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    )
    {
        // for adding price and quantity feature horizontally
        Row(modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            // calling the price box function
            PriceBox(price = price, quantity = quantity)

            // adding the quantity feature by calling the function
            QuantityAdding({quantityChange -> quantity = quantityChange})
        }

        // adding some space
        Spacer(modifier = Modifier.height(50.dp))

        // adding the button for order
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color.Black)
            .clickable(
                onClick = {
                    scope.launch{
                        snackbarHostState.showSnackbar(message = "Feature will be coming soon...",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                            )
                    }
                }
            ),
            contentAlignment = Alignment.Center
        )
        {
            TextHeading(text = "ORDER NOW",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color.White
                )
        }
    }
}

// function for the price box
@Composable
fun PriceBox(price: Int, quantity: Int)
{
    // for placing vertically the price text and the price box
    Column {
        TextHeading(text = "Price",
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.Black
        )

        // adding some space
        Spacer(modifier = Modifier.height(8.dp))

        // for the price box
        Box(modifier = Modifier
            .height(50.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color(0xFFee2a38)),
            contentAlignment = Alignment.Center
        )
        {
            // for the price text
            Text(
                text = "    $${price * quantity}    ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontFamily = FontFamily.Default
                )
            )
        }
    }
}

// function for the quantity adding feature
@Composable
fun QuantityAdding(quantityChange : (Int) -> Unit)
{
    // storing the quantity in state
    var quantity by remember { mutableIntStateOf(1) }

    // for the increment button and quantity
    Column()
    {
        // for the text
        TextHeading(
            text = "Quantity",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontFamily = FontFamily.Default,
        )

        // adding some space
        Spacer(modifier = Modifier.height(8.dp))

        // for placing horizontally the buttons
        Row(modifier = Modifier.height(45.dp))
        {
            // for the decrement button
            Box(modifier = Modifier
                .size(40.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color(0xFFee2a38))
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        // increasing quantity
                        quantity = if(quantity>1) quantity - 1 else quantity

                        quantityChange(quantity) // passing quantity to callback function
                    }
                ),
                contentAlignment = Alignment.Center
            )
            {
                TextHeading(
                    text = "−",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontFamily = FontFamily.Default,
                )
            }

            // for the quantity text
            Box(modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            )
            {
                TextHeading(
                    text = "$quantity",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = FontFamily.Default,
                )
            }

            // for the increment button
            Box(modifier = Modifier
                .size(40.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color(0xFFee2a38))
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        // increasing quantity
                        quantity = if(quantity<5) quantity + 1 else quantity

                        quantityChange(quantity) // passing quantity to callback function
                    }
                ),
                contentAlignment = Alignment.Center
            )
            {
                TextHeading(
                    text = "+",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontFamily = FontFamily.Default,
                )
            }
        }
    }
}

// function for the sub heading, heading and description
@Composable
fun TitleSubTitleDescription(heading: String, subHeading: String, description: String)
{
    // for the title
    TextHeading(
        text = heading,
        fontSize = 40.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Black,
        fontFamily = FontFamily.Serif,
    )

    // for the sub title
    TextHeading(
        text = subHeading,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray,
        fontFamily = FontFamily.Serif,
    )

    // creating some space
    Spacer(modifier = Modifier.height(30.dp))

    // for the description
    Text(text = description,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
        )
    )
}