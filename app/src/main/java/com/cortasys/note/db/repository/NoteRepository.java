package com.cortasys.note.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cortasys.note.db.AppDatabase;
import com.cortasys.note.db.dao.NoteDao;
import com.cortasys.note.db.entity.Note;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mNoteLiveData;
    public static Context context;

    public NoteRepository(Application application) {
        context = application;
        AppDatabase database = AppDatabase.getInstance(application);

        mNoteDao = database.getNoteDao();
        mNoteLiveData = mNoteDao.getAllNote();

    }

    public LiveData<List<Note>> getAllNotes() {
        return mNoteLiveData;
    }

    public void insert(Note note) {
        new insertNoteAsync(mNoteDao).execute(note);
    }

    public void delete(Note note) {
        new deleteNoteAsync(mNoteDao).execute(note);
    }

    public void deleteAll() {
        new deleteAllNotesAsync(mNoteDao).execute();
    }

    private static class deleteAllNotesAsync extends AsyncTask<Void, Void, Integer> {

        private NoteDao dao;

        public deleteAllNotesAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return dao.deleteAll();
        }

        @Override
        protected void onPostExecute(Integer val) {
            super.onPostExecute(val);
            Toast.makeText(NoteRepository.context, "Delete All :" + val, Toast.LENGTH_SHORT).show();
        }
    }

    private static class deleteNoteAsync extends AsyncTask<Note, Void, Integer> {

        private NoteDao dao;

        public deleteNoteAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Integer doInBackground(Note... notes) {
            return dao.delete(notes[0]);
        }

        @Override
        protected void onPostExecute(Integer val) {
            super.onPostExecute(val);
            Toast.makeText(NoteRepository.context, "Delete :" + val, Toast.LENGTH_SHORT).show();
        }
    }

    private static class insertNoteAsync extends AsyncTask<Note, Void, Long> {

        private NoteDao dao;

        public insertNoteAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Note... notes) {
            return dao.insert(notes[0]);
        }

        @Override
        protected void onPostExecute(Long val) {
            super.onPostExecute(val);
            Toast.makeText(NoteRepository.context, "Insert : " + val, Toast.LENGTH_SHORT).show();
        }
    }

    public void update(Note note) {
        new updateNoteAsync(mNoteDao).execute(note);
    }

    private static class updateNoteAsync extends AsyncTask<Note, Void, Integer> {

        private NoteDao dao;

        public updateNoteAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Integer doInBackground(Note... notes) {
            return dao.update(notes[0]);
        }

        @Override
        protected void onPostExecute(Integer val) {
            super.onPostExecute(val);
            Toast.makeText(NoteRepository.context, "update : " + val, Toast.LENGTH_SHORT).show();
        }
    }

}
