package com.example.roadmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Note;

public class NoteFormFragment extends Fragment {

    private Note noteToEdit;
    private int notesLength;

    public NoteFormFragment() {
        // Required empty public constructor
    }
    public NoteFormFragment(int notesLength) {
        this.notesLength = notesLength;
    }
    public NoteFormFragment(Note noteToEdit) {
        this.noteToEdit = noteToEdit;
    }

    // TODO: Rename and change types and number of parameters
    public static NoteFormFragment newInstance(String param1, String param2) {
        NoteFormFragment fragment = new NoteFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_form, container, false);

        EditText editTitle = view.findViewById(R.id.note_title_edit);
        EditText editText = view.findViewById(R.id.note_text_edit);
        Button saveNote = view.findViewById(R.id.button_save_note);
        if(noteToEdit != null)
        {
            editTitle.setText(noteToEdit.noteTitle);
            editText.setText(noteToEdit.noteText);
        }

        RoadmapViewModel roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getActivity().
                        getSupportFragmentManager().beginTransaction();
                String title = editTitle.getText().toString();
                String text = editText.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(getActivity(), "Note must have a title!", Toast.LENGTH_SHORT).show();
                    fragmentTransaction.replace(R.id.frame_layout, new NoteFormFragment(notesLength+1));
                }
                else if(noteToEdit != null){
                    noteToEdit.noteTitle = editTitle.getText().toString();
                    noteToEdit.noteText = editText.getText().toString();
                    roadmapViewModel.updateNote(noteToEdit);
                    Toast.makeText(getActivity(), "Note updated!", Toast.LENGTH_SHORT).show();
                    fragmentTransaction.replace(R.id.frame_layout, new NotesFragment());
                }
                else{
                    roadmapViewModel.insertNote(new Note(notesLength+1, title, text));
                    Toast.makeText(getActivity(), "Note added!", Toast.LENGTH_SHORT).show();
                    fragmentTransaction.replace(R.id.frame_layout, new NotesFragment());
                }
                fragmentTransaction.commit();

            }
        });

        return view;
    }
}
