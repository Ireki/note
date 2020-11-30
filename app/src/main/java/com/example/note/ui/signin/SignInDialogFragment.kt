package com.example.note.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import com.example.note.R
import com.example.note.databinding.DialogSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInDialogFragment : AppCompatDialogFragment() {

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: DialogSignInBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_sign_in,
            container,
            false
        )

        binding.viewModel = viewModel
        return binding.root
    }
}