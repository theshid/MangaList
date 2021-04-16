package com.shid.mangalist

import androidx.lifecycle.ViewModel
import com.shid.mangalist.utils.SavePref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val savePref: SavePref) :ViewModel() {

    fun loadDayNight(): Boolean {
        return savePref.loadNightMode()
    }
}