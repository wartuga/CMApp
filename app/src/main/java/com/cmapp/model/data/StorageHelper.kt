package com.cmapp.model.data

import android.content.Context

object StorageHelper {

    fun getUsername(context: Context): String {

        val username = context.getSharedPreferences("preferences", Context.MODE_PRIVATE).getString("username", "")
        return username!!
    }

    fun setUsername(context: Context, username: String) {

        val editor = context.getSharedPreferences("preferences", Context.MODE_PRIVATE).edit()
        editor.putString("username", username)
        editor.apply()
    }
}