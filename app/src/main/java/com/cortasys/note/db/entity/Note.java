package com.cortasys.note.db.entity;

import com.cortasys.note.Const;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Const.KEY_TBL_NAME)
public class Note {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = Const.KEY_N_TITLE
            , typeAffinity = ColumnInfo.TEXT)
    String title;

    @ColumnInfo(name = Const.KEY_N_SH_DESC
            , typeAffinity = ColumnInfo.TEXT)
    String shortDesc;

    @ColumnInfo(name = Const.KEY_N_DESC
            , typeAffinity = ColumnInfo.TEXT)
    String longDesc;

    @Ignore
    public Note() {
    }

    public Note(String title, String shortDesc, String longDesc) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }
}
