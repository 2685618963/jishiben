package com.example.test2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test2.bean.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="noteSQLite.db";
    private static final String TABLE_NAME_NOTE="note";
    private static final String CREATE_TABLE_SQL="create table "+TABLE_NAME_NOTE+"(id integer primary key autoincrement,title text,content text,create_time text,author text)";
    public NoteDbOpenHelper(Context context){

        super(context,DB_NAME,null,1);
    }
    public void onCreate(SQLiteDatabase db){


        db.execSQL(CREATE_TABLE_SQL);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
    public long insertData(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());
        values.put("author",note.getAuthor());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }
    public List<Note> queryAllFromDb() {

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));
                String author=cursor.getString(cursor.getColumnIndex("author"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setAuthor(author);
                note.setContent(content);
                note.setCreatedTime(createTime);

                noteList.add(note);
            }
            cursor.close();
        }

        return noteList;

    }
    public int updateData(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());
        values.put("author",note.getAuthor());
        return db.update(TABLE_NAME_NOTE, values, "id like ?", new String[]{note.getId()});
    }
    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id});
    }
}
