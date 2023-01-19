package com.example.roadmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Note;

public class NoteFormFragment extends Fragment {

    private String formType;
    private String editNoteTitle;
    private String editNoteText;
    private int notesLength;

    public NoteFormFragment() {
        // Required empty public constructor
    }
    public NoteFormFragment(int notesLength) {
        this.notesLength = notesLength;
        // Required empty public constructor
    }
    public NoteFormFragment(String formType, String editNoteTitle, String editNoteText) {
        this.formType = formType;
        this.editNoteTitle = editNoteTitle;
        this.editNoteText = editNoteText;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CoursesFragment.
     */
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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_form, container, false);

        EditText editTitle = view.findViewById(R.id.note_title_edit);
        EditText editText = view.findViewById(R.id.note_text_edit);
        Button saveNote = view.findViewById(R.id.button_save_note);

        if(formType == "edit")
        {
            editTitle.setText(editNoteTitle);
            editText.setText(editNoteText);
        }

        RoadmapViewModel roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String text = editText.getText().toString();
                roadmapViewModel.insertNote(new Note(notesLength+1, title, text));

                FragmentTransaction fragmentTransaction = getActivity().
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new NotesFragment());
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
