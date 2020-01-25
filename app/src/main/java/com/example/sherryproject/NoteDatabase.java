package com.example.sherryproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteDatabase extends SQLiteOpenHelper {


    public NoteDatabase(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Note.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Notes(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATA VARCHAR(255))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Notes");
        onCreate(db);
    }

    public void insert_notes(String data){
        ContentValues contentValues=new ContentValues();
        contentValues.put("DATA",data);
        this.getWritableDatabase().insert("Notes","",contentValues);
    }
    public Cursor getData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }


    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT ID,DATA FROM Notes";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("ID",cursor.getString(cursor.getColumnIndex("ID")));
            user.put("DATA",cursor.getString(cursor.getColumnIndex("DATA")));
            userList.add(user);
        }
        return  userList;
    }

}
