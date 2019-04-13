package com.cortasys.note.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cortasys.note.R;
import com.cortasys.note.db.entity.Note;
import com.cortasys.note.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.cortasys.note.view.AddNoteActivity.EXTRA_DESC;
import static com.cortasys.note.view.AddNoteActivity.EXTRA_ID;
import static com.cortasys.note.view.AddNoteActivity.EXTRA_SH_DESC;
import static com.cortasys.note.view.AddNoteActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;
    private static final String TAG = "MainActivity";

    NoteViewModel mNoteViewModel;
    RecyclerView mRecyclerView;
    FloatingActionButton mFabAdd;
    NoteAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFabAdd = findViewById(R.id.fabAdd);
        mRecyclerView = findViewById(R.id.mRecylerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mNoteAdapter = new NoteAdapter();
        mRecyclerView.setAdapter(mNoteAdapter);

        mNoteViewModel = ViewModelProviders
                .of(this)
                .get(NoteViewModel.class);

        mNoteViewModel.getAppNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Log.d(TAG, "onChanged: ");
//                if (notes != null) {
//                    for (int i = 0; i < notes.size(); i++)
//                        Log.d(TAG, "data : " + notes.get(i).getTitle());
//                } else Log.d(TAG, "onChanged: null");
                mNoteAdapter.submitList(notes);

            }
        });

        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mNoteViewModel.insert(
//                        new Note(
//                                "new title"
//                                , "new sh desc"
//                                , "new desc"
//                        ));

                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(mNoteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRecyclerView);


        mNoteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Log.d(TAG, "onItemClick: " + note.getTitle());
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(EXTRA_ID, note.getId());
                intent.putExtra(EXTRA_TITLE, note.getTitle());
                intent.putExtra(EXTRA_SH_DESC, note.getShortDesc());
                intent.putExtra(EXTRA_DESC, note.getLongDesc());
                startActivityForResult(intent, EDIT_REQUEST);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuDeleteAll) {
            mNoteViewModel.deleteAll();
            Toast.makeText(this, "All notes deleted.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(EXTRA_TITLE);
            String shDesc = data.getStringExtra(EXTRA_SH_DESC);
            String desc = data.getStringExtra(EXTRA_DESC);

            Note note = new Note(title, shDesc, desc);
            mNoteViewModel.insert(note);
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(EXTRA_ID,  -1);
            
            if (id == -1) {
                Toast.makeText(this, "Note cant update. something went wrong.", Toast.LENGTH_SHORT).show();
                return;
            }
            
            String title = data.getStringExtra(EXTRA_TITLE);
            String shDesc = data.getStringExtra(EXTRA_SH_DESC);
            String desc = data.getStringExtra(EXTRA_DESC);
            

            Note note = new Note(title, shDesc, desc);
            note.setId(id);
            mNoteViewModel.update(note);
        } else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }



}
