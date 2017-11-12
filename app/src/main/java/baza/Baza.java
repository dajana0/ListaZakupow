package baza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import models.Product;

/**
 * Created by Dajana on 12.11.2017.
 */

public class Baza extends SQLiteOpenHelper{
    public Baza(Context context) {
        super(context, "lista_zakupow.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lista_produktow (id INTEGER primary key autoincrement, nazwa TEXT, cena INTEGER, ilosc INTEGER, kupiono INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dodaj(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa",product.getNazwa());
        wartosci.put("cena",product.getNazwa());
        wartosci.put("ilosc",product.getNazwa());
        wartosci.put("kupiono",product.getKupiono());
        db.insertOrThrow("lista_produktow",null,wartosci);
    }
    public void usun(int id){
        SQLiteDatabase db = getWritableDatabase();
        String [] arguments = {""+id};
        db.delete("lista_produktow","id=?",arguments);
    }

    public ArrayList<Product> getAllProducts(){
        String[] kolumny = {"id","nazwa","cena","ilosc","kupiono"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("lista_produktow",kolumny,null,null,null,null,"kupiono");
        ArrayList<Product> result = new ArrayList<Product>();
        while(kursor.moveToNext()){
            Product product = new Product(kursor.getInt(0),kursor.getString(1), kursor.getInt(2), kursor.getInt(3),kursor.getInt(4));
            result.add(product);
        }
        return result;
    }
}
