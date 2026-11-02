package com.coursera.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

/**
 *Data classes for Retrieving Data from the API
 * https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json
 *
 */
@Serializable
@SerialName("MenuNetworkData")
class MenuNetworkData {
    val menu: List<MenuItemNetwork> = listOf()
}

@Serializable
@SerialName("MenuItemNetwork")
data class MenuItemNetwork(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val image: String = "",
    val category: String = "")