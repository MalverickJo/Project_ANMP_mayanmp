package com.example.mayanmp.model

import androidx.room.*

@Dao
interface DaoUser {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): User

    @Query("UPDATE User SET password=:newPass WHERE id = :userId")
    fun changePassword(newPass: String, userId: Int)

    @Query("SELECT * FROM user WHERE username = :username")
    fun checkUsername(username: String): User
}