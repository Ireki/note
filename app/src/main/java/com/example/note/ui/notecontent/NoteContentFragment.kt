package com.example.note.ui.notecontent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.note.R
import com.example.note.databinding.FragmentNoteContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteContentFragment: Fragment() {

    val viewModel : NoteContentViewModel  by viewModels()

    private val args: NoteContentFragmentArgs by navArgs()

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

        viewModel.setNote(args.noteId)
        binding.noteContentViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onPause() {
        super.onPause()
        viewModel.saveNote()
    }

}