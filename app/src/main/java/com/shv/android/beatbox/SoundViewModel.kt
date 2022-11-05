package com.shv.android.beatbox

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil

private const val TAG = "SoundViewModel"

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {

    fun onButtonClicked() {
        notifyChange()
        sound?.let {
            beatBox.play(it)
        }
    }

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = sound?.name

    var speed: Int = 100
        set(speed) {
            field = speed
            notifyChange()
        }
        get() = field


    @get:Bindable
    val playbackSpeedText: String
        get() = "Playback Speed $speed%"
}