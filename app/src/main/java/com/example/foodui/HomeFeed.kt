package com.example.foodui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Base64

// function for the home screen feed
@Composable
fun HomeFeed(navController: NavController)
{
    // storing splash screen boolean in state
    var splashScreenShow by remember { mutableStateOf(true) }

    // launching a thread
    LaunchedEffect(Unit) {
        delay(2000) // delaying the time for 2 seconds
        splashScreenShow = false // making splash screen boolean false
    }

    // if true then showing splash screen
    if(splashScreenShow)
        SplashScreen()
    // if false then showing main app feed content
    else
        MainContent(navController = navController)
}

// function for the main home feed content
@Composable
fun MainContent(navController: NavController) {

    // storing the snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    // setting the selectedOption as All in a state
    var selectedOption by remember { mutableStateOf("All") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        }
    )
    {
        // for placing everything vertically
        Column(modifier = Modifier.fillMaxSize())
        {
            // calling the app bar function
            AppBar()

            // adding some spacing
            Spacer(modifier = Modifier.height(20.dp))

            // calling search bar and settings function
            SearchBarAndSettings(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
            )

            // creating some space
            Spacer(modifier = Modifier.height(20.dp))

            // calling swipe options
            SwipeOptions(selectectText = {selectedtext -> selectedOption = selectedtext})

            // calling the function for placing images in the app
            FoodImages(snackbarHostState = snackbarHostState,
                selectectText = selectedOption,
                navController = navController
                )
        }
    }
}

// function for the app bar
@Composable
fun AppBar() {

    // creating context
    val context = LocalContext.current

    // for placing everything of the app bar
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 30.dp),
    )
    {
        // for placing the upper heading and profile horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            // for the title
            TextHeading(
                text = "FoodGo",
                fontSize = 58.sp,
                fontFamily = lobsterFont,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            // for the profile image
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .shadow(elevation = 7.dp, shape = RoundedCornerShape(16.dp))
                    .clip(shape = RoundedCornerShape(16.dp))
                    .clickable{
                        Toast.makeText(context,
                            "This feature not added yet!",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                contentAlignment = Alignment.Center
            )
            {
                // profile image
                Image(
                    painter = painterResource(id = R.drawable.elon_musk_profile),
                    contentDescription = "ProfileImage",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        //  for the sub heading
        TextHeading(
            text = "Order your favourite food!",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontFamily = FontFamily.Default
        )
    }
}

// function for the text headings
@Composable
fun TextHeading(text : String,
                fontSize : TextUnit,
                fontWeight: FontWeight,
                color : Color,
                fontFamily: FontFamily)
{
    // text
    Text(text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            fontFamily = fontFamily
        )
    )
}

// function for the search bar and settings option
@Composable
fun SearchBarAndSettings(modifier : Modifier)
{
    // storing the value as nothing for default
    var newValue by remember { mutableStateOf("") }

    val context = LocalContext.current // context creation

    // for the text field and search icon
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween)
    {
        // for giving the shadow and adjustment to search bar
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp)
            .shadow(elevation = 7.dp, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
        ) {
            // for the text field
            TextField(
                value = newValue,
                onValueChange = {
                    newValue = it
                },
                placeholder = {
                    Text(
                        "Search",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedPlaceholderColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        // for the option icon
        Box(modifier = Modifier
            .size(54.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color.Red)
            .clickable{
                Toast.makeText(context,
                    "This feature not added yet!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            contentAlignment = Alignment.Center
        )
        {
            // option image
            Image(painter = painterResource(id = R.drawable.options),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(20.dp)
                )
        }
    }
}

// function to define swipe options
@Composable
fun SwipeOptions(selectectText: (String) -> Unit)
{
    var selectedOption by remember{ mutableStateOf("All") }
    // options list for swipe options
    val swipeOption = listOf("All", "Combos", "Burgers", "Pizzas", "Desserts")

    // for placing options horizontally
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(start = 20.dp)
        .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        Alignment.CenterVertically
    )
    {
        // placing the options using loop
        swipeOption.forEach {text ->
            // box for option structure
            Box(modifier = Modifier
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(16.dp))
                .background(
                    color = if (selectedOption == text) Color(0xFFee2a38)
                    else Color.LightGray
                )
                .clickable(
                    onClick = {
                        selectedOption = text
                        // passing the selectedOption to selectedText function
                        selectectText(selectedOption)
                    }
                ),
                contentAlignment = Alignment.Center
            )
            {
                // option text
                Text(text = "     $text     ",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (selectedOption == text) Color.White
                                else Color.Black
                    )
                )
            }
        }
    }
}

// function to placing the images of foods in the app
@Composable
fun FoodImages(snackbarHostState: SnackbarHostState,
               selectectText: String,
               navController: NavController
) {

    // Storing the image list as a state that updates based on selected text
    val imageList by remember(selectectText) {
        mutableStateOf(
            when (selectectText) {
                "All" -> ImageUrls.allImages
                "Burgers" -> ImageUrls.burgers
                "Pizzas" -> ImageUrls.pizzas
                "Desserts" -> ImageUrls.desserts
                "Combos" -> ImageUrls.combos
                else -> emptyMap() // default empty map
            }
        )
    }

    // for placing every images vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .verticalScroll(rememberScrollState())
    )
    {
        // dividing into 2 sized chunks to place two image in row
        imageList.keys.chunked(2).forEach { rowList ->

            // using row to place 2 image horizontally
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            )
            {
                // placing images
                rowList.forEach { url ->

                    // calling the image box function
                    ImageInside(url = url,
                        snackbarHostState = snackbarHostState,
                        imageMap = imageList,
                        navController = navController,
                        modifier = Modifier
                            .weight(1f) // For dividing equal space
                            .height(215.dp)
                            .shadow(
                                elevation = 8.dp, // Reduced elevation for a softer shadow
                                shape = RoundedCornerShape(15.dp),
                            )
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(color = Color.White)
                    )
                }
            }
        }
    }
}

// inside the image box
@Composable
fun ImageInside(url : String,
                modifier: Modifier = Modifier,
                snackbarHostState: SnackbarHostState,
                imageMap : Map<String, List<Any>>,
                navController: NavController
                )
{
    // getting the heading and sub heading and price and description
    val heading = imageMap[url]?.get(0)
    val subHeading = imageMap[url]?.get(1)
    val price = imageMap[url]?.get(2)
    val description = imageMap[url]?.get(3)
    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())

    // using box to adding more stuffs
    Box(
        modifier = modifier.clickable(
            onClick = {
                navController.navigate("ImageZoom/${encodedUrl}/${heading}/${subHeading}/${price}/${description}")
            }
        ),
        contentAlignment = Alignment.TopCenter
    )
    {
        // using async image to get image from url
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(140.dp)
        )

        // for the bottom text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .padding(10.dp)
                .align(Alignment.BottomCenter)
        )
        {
            // for placing the fonts vertically
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.Bottom,
            )
            {
                // for main title
                TextHeading(
                    text =  heading as String, // getting the title from map,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily.Default
                )

                // for sub title
                TextHeading(
                    text =  subHeading as String, // getting the sub title
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                    fontFamily = FontFamily.Default
                )

                // creating some space
                Spacer(modifier = Modifier.height(15.dp))

                // for placing the wishlist icon and price horizontally
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                    )
                {
                    // for the price
                    Text(text = "$${price}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    // for wish list icon
                    WishListIcon(snackbarHostState = snackbarHostState,
                        url = url
                    )
                }
            }
        }
    }
}

// function for the wish list functionality
@Composable
fun WishListIcon(snackbarHostState: SnackbarHostState,
                 url: String)
{
    // putting wish list in the state
    var wishList by remember { mutableStateOf(mutableListOf<String>()) }

    // storing the couroutine scope in
    val scope = rememberCoroutineScope()

    Image(painter = painterResource(
        id = if (wishList.contains(url)) R.drawable.red_heart
        else R.drawable.heart
    ),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .size(25.dp)
        .clickable(indication = null,
            interactionSource = MutableInteractionSource(),
            onClick = {
                // checking if url string exist in set or not
                wishList = wishList.toMutableList().apply {
                    if (wishList.contains(url)) {
                        remove(url)

                        // showing removed message on SnackBar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Removed from wishlist",
                                duration = SnackbarDuration.Short,
                                actionLabel = "Dismiss"
                            )
                        }
                    } else {
                        add(url)

                        // showing added message on SnackBar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Added to wishlist",
                                duration = SnackbarDuration.Short,
                                actionLabel = "View"
                            )
                        }
                    }
                }
            }
        )
    )
}

