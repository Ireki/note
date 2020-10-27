package com.example.note.ui.notecontent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.note.R
import com.example.note.databinding.FragmentNoteContentBinding
import com.example.note.util.provideRepository

class NoteContentFragment: Fragment() {

    private lateinit var viewModel : NoteContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNoteContentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_content,
            container,
            false
        )


        val factory = NoteContentViewModelFactory(provideRepository(requireContext()), NoteContentFragmentArgs.fromBundle(requireArguments()).noteId)
        viewModel = ViewModelProvider(this, factory).get(NoteContentViewModel::class.java)

        binding.noteContentViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onPause() {
        super.onPause()
        viewModel.saveNote()
    }

}