package com.coursera.littlelemon.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.coursera.littlelemon.MenuItem
import com.coursera.littlelemon.MenuViewModel
import com.coursera.littlelemon.R
import com.coursera.littlelemon.ToolbarScreenProfileIcon
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
    viewModel: MenuViewModel = koinViewModel(),
) {

    val items by viewModel.menuItems.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    //Navigation bar (logo, profile icon)
    //Hero Section (heading, subheading, about, image, search)
    //Menu Breakdown (delivery text, menu sections buttons
    //Menu Items (name of dish, picture of dish, description, price)
    Scaffold(
        topBar = {
            ToolbarScreenProfileIcon(
                title = "Little Lemon",
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            //Hero Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF495E57))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Little Lemon",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif,
                        color = Color.Yellow
                    )
                    Text(
                        text = "Chicago",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.Gray
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.hero_image),
                            contentDescription = "Little Lemon restaurant dish",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(130.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                    val searchIcon = Icons.Default.Search
                    TextField(
                        value = searchQuery,
                        onValueChange = { viewModel.queryMenuItems(it) },
                        singleLine = true,
                        placeholder = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = searchIcon,
                                contentDescription = "Search icon",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDEFEE),
                            unfocusedContainerColor = Color(0xFFEDEFEE),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                }
            }

            //Menu Breakdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "ORDER FOR DELIVERY!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    for (category in listOf("Starters", "Mains", "Desserts", "Drinks")) {
                        CategoryButton(
                            category = category,
                            selected = category.equals(selectedCategory, ignoreCase = true),
                            onClick = { viewModel.selectCategory(category) }
                        )
                    }

                }
                //Menu Items
                LazyColumn {

                    items(items, key={it.id}){
                        MenuItems(it)
                    }
                }

            }
        }
    }
}


@Composable
private fun RowScope.CategoryButton(category: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .weight(1f)
            .padding(4.dp),
        contentPadding = PaddingValues(12.dp, 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFF4CE14) else Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text(text = category, fontSize = 10.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun MenuItems(menuItem: MenuItem = MenuItem()) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                menuItem.title,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(menuItem.description, modifier = Modifier.padding(bottom = 8.dp), maxLines = 2)
            Text(
                menuItem.price,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            val context = LocalPlatformContext.current
            val density = LocalDensity.current
            val imageRequest = remember(menuItem.image) {
                ImageRequest.Builder(context)
                    .data(menuItem.image)
                    .size(with(density) { 100.dp.roundToPx() })
                    .memoryCacheKey(menuItem.image)
                    .diskCacheKey(menuItem.image)
                    .build()
            }
            AsyncImage(
                model = imageRequest,
                contentDescription = menuItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}


