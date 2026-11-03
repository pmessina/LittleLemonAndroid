package com.coursera.littlelemon

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

private fun MenuItemNetwork.toMenuItem() =
    MenuItem(id, title, description, price, image, category)

class MenuRepository(
    private val httpClient: HttpClient = NetworkClient.instance

) {
    val menuItems: Flow<List<MenuItem>> = flow {
        emit(fetchMenu().map { it.toMenuItem() })
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val menuNetworkData =
            httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body<MenuNetworkData>()
        return menuNetworkData.menu
    }
}

object NetworkClient {
    val instance by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                val jsonConfig = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
                json(jsonConfig)
                // raw.githubusercontent.com serves JSON as text/plain
                register(ContentType.Text.Plain, KotlinxSerializationConverter(jsonConfig))
            }
        }
    }
}

