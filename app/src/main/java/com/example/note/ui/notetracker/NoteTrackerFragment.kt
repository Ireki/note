package com.example.note.ui.notetracker

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
import com.google.android.material.snackbar.Snackbar

class NoteTrackerFragment : Fragment(){

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

        binding.setLifecycleOwner(this)

        noteTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if(it ==true){
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "All your data is gone forever",
                    Snackbar.LENGTH_SHORT
                ).show()
                noteTrackerViewModel.doneShowingSnackbar()
            }
        })

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
            true
        })

        binding.noteList.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }



}