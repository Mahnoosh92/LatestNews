package com.mahnoosh.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mahnoosh.designsystem.ui.theme.LatesNewsAppTheme

@Composable
fun NewsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun NewsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    NewsButton(onClick = onClick, modifier = modifier, enabled = enabled) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(modifier = Modifier.padding(start = if (leadingIcon == null) 0.dp else 8.dp)) {
            text()
        }

    }
}

@Composable
fun NewsTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick, modifier = modifier, enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
    ) { content() }
}

@Composable
fun NewsTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    leadingIcon: @Composable (() -> Unit)?,
    text: @Composable () -> Unit
) {
    NewsTextButton(
        onClick = onClick, modifier = modifier, enabled = enabled,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(modifier = Modifier.padding(start = if (leadingIcon == null) 0.dp else 8.dp)) {
            text()
        }
    }
}

@Composable
fun NewsOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick, modifier = modifier, enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.onSurface
            },
        ),
    ) { content() }
}

@Composable
fun NewsOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    leadingIcon: @Composable (() -> Unit)? = null,
    text: @Composable () -> Unit
) {
    NewsOutlinedButton(onClick = onClick, modifier = modifier, enabled = enabled, content = {
        if (leadingIcon != null)
            leadingIcon()
        else {
            Box(modifier = Modifier.padding(start = if (leadingIcon == null) 0.dp else 8.dp)) {
                text()
            }
        }
    })
}

@ThemePreviews
@Composable
fun PreviewNewsButton() {
    LatesNewsAppTheme {
        NewsButton(onClick = {}) {
            Text(text = "Sample")
        }
    }
}

@ThemePreviews
@Composable
fun PreviewNewsButtonWithLeadingIcon() {
    LatesNewsAppTheme {
        NewsButton(
            onClick = {},
            text = { Text(text = "Sample") },
            leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
    }
}

@ThemePreviews
@Composable
fun PreviewNewsOutlinedButton() {
    LatesNewsAppTheme {
        NewsOutlinedButton(onClick = {}, content = {Text("sample")})
    }
}