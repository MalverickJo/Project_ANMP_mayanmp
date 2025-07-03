package com.example.mayanmp.model

import android.content.Context

class SharePreferences (context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveLogin(userId: Int) {
        prefs.edit().putInt("USER_ID", userId).putBoolean("IS_LOGIN", true).apply()
    }
    fun getuser(): Int = prefs.getInt("USER_ID", 0)

    fun isLogin(): Boolean = prefs.getBoolean("IS_LOGIN", false)

    fun getUserId(): Int = prefs.getInt("USER_ID", -1)

    fun logout() {
        prefs.edit().clear().apply()
    }
}