package com.example.note.ui.notetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.R
import com.example.note.data.Note
import com.example.note.databinding.FragmentNoteTrackerBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class NoteTrackerFragment : Fragment(){



    companion object {
        const val TAG = "LoginFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNoteTrackerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_tracker,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = NoteTrackerViewModelFactory(application)

        val noteTrackerViewModel = ViewModelProvider(this, viewModelFactory).get(NoteTrackerViewModel::class.java)

        binding.noteTrackerViewModel = noteTrackerViewModel

        val adapter = NoteListAdapter(NoteListener { noteId ->
            noteTrackerViewModel.onNoteClicked(noteId)
        })

        binding.noteList.adapter = adapter


        noteTrackerViewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setNotes(it)
            }
        })

        binding.lifecycleOwner = this



        noteTrackerViewModel.navigateNoteContent.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                this.findNavController().navigate(
                    NoteTrackerFragmentDirections
                        .fragmentNoteTrackerToFragmentNoteContent(note))
                noteTrackerViewModel.onNoteContentNavigated()
            }
        })

        binding.bottomAppBar.setOnMenuItemClickListener( Toolbar.OnMenuItemClickListener() { item: MenuItem? ->
            if (item?.itemId == R.id.menu_settings){
                this.findNavController().navigate(
                    NoteTrackerFragmentDirections
                        .fragmentNoteTrackerToFragmentSettings()
                )
            }
            if (item?.itemId == R.id.menu_authentication) launchSignInFlow()
            true
        })


        binding.noteList.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }

    private fun launchSignInFlow() {

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(AuthUI
            .getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build(),
            SIGN_IN_RESULT_CODE
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
            }
        }
    }

}