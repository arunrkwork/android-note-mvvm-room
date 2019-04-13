package com.cortasys.note.viewmodel;

import android.app.Application;
import android.util.Log;

import com.cortasys.note.db.entity.Note;
import com.cortasys.note.db.repository.NoteRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private static final String TAG = "NoteViewModel";
    private NoteRepository mNoteRepository;
    private LiveData<List<Note>> mListLiveData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);
        mListLiveData = mNoteRepository.getAllNotes();
    }

    public void insert(Note note) {
        mNoteRepository.insert(note);
    }

    public void update(Note note) {
        mNoteRepository.update(note);
    }

    public void delete(Note note) {
        mNoteRepository.delete(note);
    }

    public LiveData<List<Note>> getAppNotes() {
        Log.d(TAG, "getAppNotes: start");
//        if (mListLiveData == null) {
//            Log.d(TAG, "getAppNotes: inner");
//            mListLiveData = mNoteRepository.getAllNotes();
//        }
        return mListLiveData;
    }


    public void deleteAll() {
        mNoteRepository.deleteAll();
    }
}
