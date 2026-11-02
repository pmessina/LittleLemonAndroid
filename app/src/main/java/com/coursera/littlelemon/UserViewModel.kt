package com.coursera.littlelemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    // null until the database has answered, so the UI can avoid showing the wrong screen
    val userExists: StateFlow<Boolean?> = repository.userExists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val users: StateFlow<List<User>> = repository.users
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val currentUser: StateFlow<User?> = repository.users
        .map { it.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun addUser(user: User) {
        viewModelScope.launch { repository.addUser(user) }
    }

    fun logout() {
        viewModelScope.launch { repository.logout() }
    }
}
