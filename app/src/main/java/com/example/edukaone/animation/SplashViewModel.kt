package com.example.edukaone.animation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel {
    class SplashViewModel : ViewModel() {

        private val _navigateToMain = MutableLiveData<Boolean>()
        val navigateToMain: LiveData<Boolean>
            get() = _navigateToMain

        init {
            viewModelScope.launch {
                delay(3000) // 3 seconds
                _navigateToMain.value = true
            }
        }
    }
}