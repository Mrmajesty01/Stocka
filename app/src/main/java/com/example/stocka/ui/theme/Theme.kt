package com.example.stocka.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.stocka.ui.theme.ListOfColors.blue
import com.example.stocka.ui.theme.ListOfColors.white
import com.example.stocka.ui.theme.ListOfColors.yellow

private val DarkColorPalette = darkColors(
    primary = yellow,
    primaryVariant = blue,
    secondary = white
)

private val LightColorPalette = lightColors(
    primary = yellow,
    primaryVariant = blue,
    secondary = white

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun StockaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}