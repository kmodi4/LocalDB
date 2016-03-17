package com.fb.markzuck.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAdapter  {

    DBhelper dBhelper;
    List<String> list;
    Map<String,String> pair;
    public DBAdapter(Context context){
        dBhelper = new DBhelper(context);
        list = new ArrayList<String>();
        pair = new HashMap<String, String>();
    }

    public long InsertData(String name,String Password){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBhelper.Name,name);
        contentValues.put(DBhelper.Password,Password);
        long id = db.insert(DBhelper.Table_Name,null,contentValues);
        return id;
    }

    public String getData(){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String[] col = {DBhelper.Uid,DBhelper.Name,DBhelper.Password};
        Cursor cursor = db.query(DBhelper.Table_Name,col,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DBhelper.Uid);
            int cid = cursor.getInt(index1);
            String name = cursor.getString(1);
            String pass = cursor.getString(2);
            buffer.append("cid:"+cid+" Name:"+name+" Password:"+pass+"\n");

        }
        return buffer.toString();
    }

    public List<String> getAdapterData(){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String[] col = {DBhelper.Uid,DBhelper.Name,DBhelper.Password};
        Cursor cursor = db.query(DBhelper.Table_Name,col,null,null,null,null,null);


        while (cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DBhelper.Uid);
            int cid = cursor.getInt(index1);
            String name = cursor.getString(1);
            list.add(name);
            String pass = cursor.getString(2);
            pair.put(name,pass);

        }
        return list;
    }

    public Map<String,String> getmap(){
        return pair;
    }


    public String Userdata(String name){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String[] col = {DBhelper.Uid,DBhelper.Name,DBhelper.Password};
        Cursor cursor = db.query(DBhelper.Table_Name,col,DBhelper.Name+" = '"+name+"'",null,null,null,null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DBhelper.Name);
            int index2 = cursor.getColumnIndex(DBhelper.Password);
            String personname = cursor.getString(index1);
            String pass = cursor.getString(index2);
            buffer.append(" Name:"+personname+" Password:"+pass+"\n");

        }
        return buffer.toString();
       /* Select id from MyDB where name=? and password=?
       String[] col = {DBhelper.Uid};
       String[] selArg = {name,password};  // parameter
        Cursor cursor = db.query(DBhelper.Table_Name,col,DBhelper.Name+" = ? AND "+DBhelper.Password+" =?",selArg,null,null,null);*/

    }

    public int updateData(String old,String nw){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBhelper.Name,nw);

        int count = db.update(DBhelper.Table_Name, contentValues, DBhelper.Name + " =? ", new String[]{old});
        return count;
    }

    public int delData(String name){
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String[] arg = {name};
        int c = db.delete(DBhelper.Table_Name, DBhelper.Name + " =? ", arg);
        return c;
    }



   static class DBhelper extends SQLiteOpenHelper{
        private static final String DataBase_Name = "MyDB";
        private static final String Table_Name = "Login";
        private static final int DataBase_version = 1;
        private static final String Uid = "_id";
        private static final String Name = "Name";
        private static final String Password = "Password";
        private static final String Create_Table = "CREATE TABLE "+Table_Name+" ("+Uid+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Name+" VARCHAR(255), "+Password+" VARCHAR(255));";
        private static final String Drop_Table = "DROP TABLE IF EXISTS "+Table_Name;
        private Context context;

        public DBhelper(Context context){
            super(context,DataBase_Name,null,DataBase_version);
            this.context = context;
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(Create_Table);
            }catch (SQLException e){
                e.printStackTrace();
                Toast.makeText(context,"SQLError",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(Drop_Table);
                onCreate(sqLiteDatabase);
            }catch (SQLException e){
                e.printStackTrace();
                Toast.makeText(context,"SQLError",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
