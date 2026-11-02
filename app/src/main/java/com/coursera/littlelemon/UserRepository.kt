package com.coursera.littlelemon

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val users: Flow<List<User>> = userDao.getUsers()

    val userExists: Flow<Boolean> = userDao.userExists()

    suspend fun addUser(user: User) = userDao.insert(user)

    suspend fun logout() = userDao.deleteAll()
}
