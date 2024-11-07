package com.example.foodui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

// inheriting the features of ComponentActivity()
class MainActivity : ComponentActivity()
{
    // overriding onCreate function
    override fun onCreate(savedInstanceState : Bundle?)
    {
        // calling the super class's onCreate function
        super.onCreate(savedInstanceState)
        // full filling the activity till the status bar
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Black.toArgb())
        )
        // senContent() function to mention the UI components
        setContent()
        {
            // calling Navigation details function
            NavigationDetails()
        }
    }
}

// function for defining all the navigation details
@Composable
fun NavigationDetails()
{
    // navController instance
    val navController = rememberNavController()

    // NavHost declaration
    NavHost(navController = navController,
        startDestination = "HomeFeed"
    )
    {
        // Home Feed composable
        composable("HomeFeed") // Main Activity
        {
            // calling HomeFeed function
            HomeFeed(navController = navController)
        }

        // image zoom composable
        composable(route = "ImageZoom/{url}/{heading}/{subHeading}/{price}/{description}",
            arguments = listOf(
                navArgument(name = "url"){ type = NavType.StringType },
                navArgument(name = "heading"){ type = NavType.StringType },
                navArgument(name = "subHeading"){ type = NavType.StringType },
                navArgument(name = "price"){ type = NavType.IntType },
                navArgument(name = "description"){ type = NavType.StringType}
            )
        )
        {backStackEntry ->

            // defining the variables
            val url = URLDecoder.decode(backStackEntry.arguments?.getString("url"), // decoding url
                StandardCharsets.UTF_8.toString())
            val heading = backStackEntry.arguments?.getString("heading")
            val subHeading = backStackEntry.arguments?.getString(("subHeading"))
            val price = backStackEntry.arguments?.getInt("price")
            val description = backStackEntry.arguments?.getString("description")

            // calling ImageZoom function
            ImageZoom(
                url = url!!,
                heading = heading!!,
                subHeading = subHeading!!,
                price = price!!,
                description = description!!,
                navController = navController
                )
        }

        // home feed main content composable
        composable(route = "HomeFeedMainContent")
        {
            // calling the main content function
            MainContent(navController = navController)
        }
    }
}

