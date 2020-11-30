package com.example.note.util.signin

import com.firebase.ui.auth.FirebaseUiException

sealed class SignInResult
object SignInSuccess : SignInResult()
data class SignInFailed(val error: FirebaseUiException?) : SignInResult()