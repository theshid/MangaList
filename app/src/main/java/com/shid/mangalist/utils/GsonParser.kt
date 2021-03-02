package com.shid.mangalist.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonParser {

    private var gson: Gson? = null

    fun getGsonParser(): Gson? {
        if (gson == null) {
            val builder = GsonBuilder()
            gson = builder.create()
        }
        return gson
    }
}