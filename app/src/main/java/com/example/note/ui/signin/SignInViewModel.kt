package com.example.note.ui.signin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel @ViewModelInject constructor(): ViewModel(){


    fun onSignIn() {
        viewModelScope.launch {
            Timber.d("Sign in requested")
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            Timber.d("Sign out requested")
        }
    }

    fun onCancel() {
    }
}