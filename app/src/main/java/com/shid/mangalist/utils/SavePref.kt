package com.shid.mangalist.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavePref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("animeSave", Context.MODE_PRIVATE)


    fun setNightMode(state: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean("NightMode", state)
        editor.apply()
    }

    fun loadNightMode(): Boolean {
        return sharedPref.getBoolean("NightMode", false)
    }

}