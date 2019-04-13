package com.cortasys.note.db.dao;

import com.cortasys.note.Const;
import com.cortasys.note.db.entity.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    long insert(Note note);

    @Update
    int update(Note note);

    @Query("select * from " + Const.KEY_TBL_NAME)
    LiveData<List<Note>> getAllNote();

    @Delete
    int delete(Note note);

    @Query("DELETE FROM " + Const.KEY_TBL_NAME)
    int deleteAll();
}
