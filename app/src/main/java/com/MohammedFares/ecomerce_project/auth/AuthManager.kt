package com.MohammedFares.ecomerce_project.auth

import android.content.Context
import android.content.SharedPreferences
import com.MohammedFares.ecomerce_project.comon.Constantes


class AuthManager( private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    var id: Long
        get() = sharedPreferences.getLong(KEY_ID, 1)
        set(value) = sharedPreferences.edit().putLong(KEY_ID, value).apply()

    var cartId: Long
        get() = sharedPreferences.getLong(KEY_CART_ID,-1)
        set(value) = sharedPreferences.edit().putLong(KEY_CART_ID, value).apply()

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()

    var profile: String?
        get() = sharedPreferences.getString(KEY_PROFILE, null)
        set(value) = sharedPreferences.edit().putString(KEY_PROFILE, value).apply()

    fun login(username: String, id: Long,email: String, profile: String) {
        isLoggedIn = true
        this.username = username
        this.id = id
        this.email = email
        this.profile = profile
    }

    fun logout() {
        isLoggedIn = false
        username = null
        id = 1
        email = null
        profile = null
    }

    fun isAdminLoggedIn(): Boolean {
        return isLoggedIn && profile == Constantes.ADMIN_KEY
    }

    fun isClientLoggedIn(): Boolean {
        return isLoggedIn && profile == Constantes.CLIENT_KEY
    }

    companion object {
        private const val PREF_NAME = "AuthSharedPrefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_ID = "id"
        private const val KEY_EMAIL = "email"
        private const val KEY_PROFILE = "profile"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_CART_ID = "cartId"
    }
}
