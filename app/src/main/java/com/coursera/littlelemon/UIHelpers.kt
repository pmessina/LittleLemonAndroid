package com.coursera.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarScreen(title: String, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(width = 200.dp, height = 50.dp)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Logo"
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun ToolbarScreenProfileIcon(
    title: String = "",
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(width = 200.dp, height = 50.dp)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Logo"
            )
        }, actions = {
            Image(
                painterResource(R.drawable.profile),
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    .clickable(onClickLabel = "Open profile", onClick = onProfileClick),
                contentDescription = "Profile Icon",
                contentScale = ContentScale.Fit
            )


        }
    )
}