package com.mahnoosh.designsystem

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.topBorder(strokeWidth: Dp, color: Color) = this.then(
    Modifier.drawWithContent {
        drawContent() // Renders the composable's original content first

        // Draw the border line at the top
        drawLine(
            color = color,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f),
            strokeWidth = strokeWidth.toPx()
        )
    }
)