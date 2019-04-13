package com.cortasys.note.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cortasys.note.Const;
import com.cortasys.note.db.dao.NoteDao;
import com.cortasys.note.db.entity.Note;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = { Note.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static final String DB_NAME = Const.KEY_DB_NAME;
    private static volatile AppDatabase instance;

    public abstract NoteDao getNoteDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                instance = create(context);
            }
        }
        return instance;
    }

    private static AppDatabase create(Context context) {
        return Room.databaseBuilder(context
                , AppDatabase.class
                , DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(callback)
                .build();
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "onCreate: call back");
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public PopulateDbAsyncTask(AppDatabase db) {
            this.noteDao = db.getNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: call back");
            noteDao.insert(new Note("Title 1", "Sh 1", "Desc 1"));
            noteDao.insert(new Note("Title 2", "Sh 2", "Desc 2"));
            noteDao.insert(new Note("Title 3", "Sh 3", "Desc 3"));
            return null;
        }

    }

}
