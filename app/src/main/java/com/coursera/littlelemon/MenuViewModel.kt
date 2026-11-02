package com.coursera.littlelemon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MenuViewModel(repository: MenuRepository) : ViewModel() {
    private val query = MutableStateFlow("")
    val searchQuery: StateFlow<String> = query
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    val menuItems: StateFlow<List<LLMenuItem>> =
        combine(
            repository.menuItems.catch {
                Log.e("MenuViewModel", "Failed to load menu", it)
                emit(emptyList())
            },
            query,
            _selectedCategory
        ) { items, q, category ->
            items.filter {
                it.title.contains(q, ignoreCase = true) &&
                    (category == null || it.category.equals(category, ignoreCase = true))
            }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun queryMenuItems(query: String) {
        this.query.value = query
    }

    // Selecting the active category again clears the filter
    fun selectCategory(category: String) {
        _selectedCategory.value = if (_selectedCategory.value.equals(category, ignoreCase = true)) null else category
    }
}
