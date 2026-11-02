package com.coursera.littlelemon

import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) > 0 FROM users")
    fun userExists(): Flow<Boolean>

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

    @Insert
    suspend fun insert(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val email: String,
    val phone: String,
    val firstName: String,
    val lastName: String,
)

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
