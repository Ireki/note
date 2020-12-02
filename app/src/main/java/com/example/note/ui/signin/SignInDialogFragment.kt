package com.example.note.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.note.BuildConfig
import com.example.note.R
import com.example.note.databinding.DialogSignInBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInDialogFragment : AppCompatDialogFragment() {

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: DialogSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_sign_in,
            container,
            false
        )

        auth = Firebase.auth

        binding.signIn.setOnClickListener { startSignIn() }
        binding.signOut.setOnClickListener { signOut() }

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                // Sign in succeeded
                updateUI(auth.currentUser)
            } else {
                // Sign in failed
                Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun startSignIn() {

        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
            .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()))
            .setLogo(R.mipmap.ic_launcher)
            .build()

        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Signed in
            binding.status.text = user.email
            binding.signIn.visibility = View.GONE
            binding.signOut.visibility = View.VISIBLE
        } else {
            // Signed out
            binding.status.setText(R.string.welcome)
            binding.signIn.visibility = View.VISIBLE
            binding.signOut.visibility = View.GONE
        }
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(requireContext())
        updateUI(null)
    }


    companion object {
        private const val RC_SIGN_IN = 9001
    }


}