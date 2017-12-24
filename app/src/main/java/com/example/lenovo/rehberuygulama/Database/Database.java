package com.example.lenovo.rehberuygulama.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lenovo.rehberuygulama.Model.Kisi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 23.12.2017.
 */

public class Database {
    private int VERSION = 1;
    private String DATABASE_NAME = "database.db";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public Database(Context _context) {
        context = _context;
        dbHelper = new DatabaseHelper(_context);

    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, VERSION);
        }

         @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL("CREATE TABLE [Kisiler](\n" +
                                                                "[id] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                                                                "[ad_soyad] TEXT, \n" +
                                                                "[email] TEXT, \n" +
                                                                "[cinsiyet] TEXT, \n" +
                                                                "[tel_no] TEXT);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public Database open(){

        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){

        db.close();
    }

    public void KisiEkle(Kisi kisi){

        open();
        ContentValues values = new ContentValues();
        values.put("ad_soyad",kisi.getAdveSoyad());
        values.put("email",kisi.getEmail());
        values.put("cinsiyet",kisi.getCinsiyet());
        values.put("tel_no",kisi.getTelNo());
        db.insert("Kisiler",null,values);
        close();
    }

    public List<Kisi> KisiListele(){

        open();
        List<Kisi> kisiler = new ArrayList<Kisi>();
        Cursor cursor = db.rawQuery("SELECT * FROM Kisiler",null);
        if(cursor != null && !cursor.isClosed()){

            while (cursor.moveToNext()){
                Kisi kisi = new Kisi();
                kisi.setId(cursor.getInt(cursor.getColumnIndex("id")));
                kisi.setAdveSoyad(cursor.getString(cursor.getColumnIndex("ad_soyad")));
                kisi.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                kisi.setTelNo(cursor.getString(cursor.getColumnIndex("tel_no")));
                kisi.setCinsiyet(cursor.getString(cursor.getColumnIndex("cinsiyet")));
                kisiler.add(kisi);
            }

            cursor.close();

        }
        close();
        return kisiler;
    }

    public void KisiSil(int id){
        open();
        db.delete("Kisiler","id="+id,null);
        close();
    }

    public void KisiGuncelle(Kisi kisi){
        open();
        ContentValues values = new ContentValues();
        values.put("ad_soyad",kisi.getAdveSoyad());
        values.put("email",kisi.getEmail());
        values.put("cinsiyet",kisi.getCinsiyet());
        values.put("tel_no",kisi.getTelNo());
        db.update("Kisiler",values,"id="+kisi.getId(),null);
        close();

    }


    public Kisi KisiGetir(int id){

        Kisi kisi = new Kisi();

        Cursor cursor = db.rawQuery("SELECT * FROM Kisiler WHERE id="+id ,null);
        if(cursor != null && !cursor.isClosed()){

            while (cursor.moveToNext()){

                kisi.setId(cursor.getInt(cursor.getColumnIndex("id")));
                kisi.setAdveSoyad(cursor.getString(cursor.getColumnIndex("ad_soyad")));
                kisi.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                kisi.setTelNo(cursor.getString(cursor.getColumnIndex("tel_no")));
                kisi.setCinsiyet(cursor.getString(cursor.getColumnIndex("cinsiyet")));
            }
            cursor.close();
        }
        close();
        return kisi;
    }
}

