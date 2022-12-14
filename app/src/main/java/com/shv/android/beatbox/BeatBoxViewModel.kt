package com.shv.android.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BeatBoxViewModel(assets: AssetManager) : ViewModel() {

    var beatBox = BeatBox(assets)

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }

    class BeatBoxViewModelFactory(private val assets: AssetManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(AssetManager::class.java).newInstance(assets) as T
        }
    }
}
