package com.example.note.util.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface SignInHandler {

    fun makeSignInIntent(): LiveData<Intent?>

    fun signIn(resultCode: Int, data: Intent?, onComplete: (SignInResult) -> Unit)

    fun signOut(context: Context, onComplete: () -> Unit = {})
}

class FirebaseAuthSignInHandler(private val externalScope: CoroutineScope) : SignInHandler {

    override fun makeSignInIntent(): LiveData<Intent?> {

        val result = MutableLiveData<Intent?>()

        // Run on background because AuthUI does I/O operations.
        externalScope.launch {
            // this is mutable because FirebaseUI requires it be mutable
            val providers = mutableListOf(
                AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(
                    GoogleSignInOptions.Builder()
                        .requestId()
                        .requestEmail()
                        .build()
                ).build()
            )

            result.postValue(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()
            )
        }
        return result
    }


    @SuppressWarnings("unused")
    override fun signIn(
        resultCode: Int,
        data: Intent?,
        onComplete: (SignInResult) -> Unit
    ) {
        when (resultCode) {
            Activity.RESULT_OK -> onComplete(SignInSuccess)
            else -> onComplete(SignInFailed(IdpResponse.fromResultIntent(data)?.error))
        }
    }


    override fun signOut(context: Context, onComplete: () -> Unit) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener { onComplete() }
    }
}
