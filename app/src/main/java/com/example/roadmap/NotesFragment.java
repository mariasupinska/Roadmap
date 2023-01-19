package com.example.roadmap;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Note;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private RoadmapViewModel roadmapViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    public static final String KEY_EXTRA_NOTE_ID = "note_id";

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView contentTextView;
        private Note note;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.single_note, parent, false));
            itemView.setOnClickListener(this);

            contentTextView = itemView.findViewById(R.id.note_item_title);
            titleTextView = itemView.findViewById(R.id.note_item_name);
        }

        public void bind(Note note){
            this.note = note;
            titleTextView.setText(note.noteTitle);
            contentTextView.setText(note.noteText);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_NOTE_ID, note.noteId);
            startActivity(intent);
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> notes;

        @NonNull
        @Override
        public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            return new NoteHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteHolder holder, int position){
            Log.d("onBindViewHolder", "WCHODZE SE TU");
            if(notes != null){
                Note note = notes.get(position);
                holder.bind(note);
            }
            else
                Log.d("MainActivity", "No notes");
        }
        @Override
        public int getItemCount() {
            if(notes != null)
                return notes.size();
            else
                return 0;
        }

        void setNotes(List<Note> notes){
            this.notes = notes;
            notifyDataSetChanged();
        }
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.notes_recycler_view);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        roadmapViewModel.findAllNotes().observe(getViewLifecycleOwner(), adapter::setNotes);

        Button addNoteButton;
        addNoteButton = view.findViewById(R.id.add_note_button);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                FragmentTransaction fragmentTransaction = getActivity().
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new AddNoteFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}