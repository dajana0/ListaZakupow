package dajana.listazakupow;

import android.app.Dialog;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.CustomAdapter;
import baza.Baza;
import baza.ProductProvider;
import models.Product;

public class ListActivity extends AppCompatActivity {
    final Context context =  this;
    ListView lvProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lvProducts = (ListView) findViewById(R.id.lista_produktow) ;
        zbudujListe();
        lvProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.confirm_dialog);
                dialog.setTitle("Potwierdź akcje");

                Button btAnuluj = (Button) dialog.findViewById(R.id.btAnuluj);
                btAnuluj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                final int productID = view.getId();
                Button btZatwierdz = (Button) dialog.findViewById(R.id.btZatwierdz);
                btZatwierdz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Baza baza = new Baza(ListActivity.this);
                       // baza.usun(productID);
                        String [] arguments = {""+productID};
                        getContentResolver().delete(ProductProvider.CONTENT_URI, "id=?",arguments);
                        dialog.dismiss();
                        zbudujListe();
                    }
                });
                dialog.show();
                return true;
            }
        });
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.product_dialog);
                dialog.setTitle("Edycja produktu");

                Button btAnuluj = (Button) dialog.findViewById(R.id.btAnuluj);
                btAnuluj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                final EditText nazwa = (EditText) dialog.findViewById(R.id.etNazwaProduktu);
                final EditText ilosc = (EditText) dialog.findViewById(R.id.etIlosc);
                final EditText cena = (EditText) dialog.findViewById(R.id.etCena);
                final CheckBox kupiono = (CheckBox) dialog.findViewById(R.id.cbKupione);
                //final Baza baza = new Baza(ListActivity.this);
                //String URL = "content://dajana.listazakupow.ProductsProvider";

                //final Uri products = Uri.parse(URL);
                String [] arguments = {""+String.valueOf(view.getId())};
                Cursor baza =  getContentResolver().query(ProductProvider.CONTENT_URI,null,"id=?",arguments,null);
                baza.moveToFirst();
               // final Product p = baza.pobierz(view.getId());
                final Product p = new Product(baza.getInt(0),baza.getString(1),baza.getInt(2),baza.getInt(3),baza.getInt(4));
                nazwa.setText(p.getNazwa());
                ilosc.setText(String.valueOf(p.getIlosc()));
                cena.setText(String.valueOf(p.getCena()));
                kupiono.setChecked(p.getKupiono()==1? true :false);
                nazwa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nazwa.getCurrentTextColor()==Color.RED){
                            nazwa.setText("");
                            SharedPreferences settings = getSharedPreferences("font_color", 0);
                            nazwa.setTextColor(settings.getInt("color",Color.BLACK));
                            nazwa.setTypeface(null, Typeface.NORMAL);
                        }
                    }
                });
                Button btZatwierdz = (Button) dialog.findViewById(R.id.btZatwierdz);
                btZatwierdz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues values = new ContentValues();
                        if(nazwa.getText()== null || nazwa.getText().length() == 0){
                            nazwa.setTextColor(Color.RED);
                            nazwa.setTypeface(null, Typeface.BOLD);
                            nazwa.setText("Wpisz nazwę!");
                        }else{
                            //Baza baza = new Baza(ListActivity.this);

                            Product product = new Product(nazwa.getText().toString(),kupiono.isChecked() == false? 0:1);
                            product.setId(p.getId());
                            if(ilosc.getText().length() == 0){
                                product.setIlosc(1);
                            }else{
                                product.setIlosc(Integer.parseInt(ilosc.getText().toString()));
                            }
                            if(cena.getText().length() == 0){
                                product.setCena(0);
                            }else{
                                product.setCena(Double.parseDouble(cena.getText().toString()));
                            }
                            values.put(ProductProvider.NAZWA, product.getNazwa());
                            values.put(ProductProvider.CENA, product.getCena() * 100);
                            values.put(ProductProvider.ILOSC, product.getIlosc());
                            values.put(ProductProvider.KUPIONO, product.getKupiono());
                            getContentResolver().update(ProductProvider.CONTENT_URI,values, "id="+ product.getId(),null);
                            //baza.zmien(product);
                            zbudujListe();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.product_dialog);
                dialog.setTitle("Nowy produkt");

                Button btAnuluj = (Button) dialog.findViewById(R.id.btAnuluj);
                btAnuluj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                final EditText nazwa = (EditText) dialog.findViewById(R.id.etNazwaProduktu);
                nazwa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nazwa.getCurrentTextColor()==Color.RED){
                            nazwa.setText("");
                            SharedPreferences settings = getSharedPreferences("font_color", 0);
                            nazwa.setTextColor(settings.getInt("color",Color.BLACK));
                            nazwa.setTypeface(null, Typeface.NORMAL);
                        }
                    }
                });
                Button btZatwierdz = (Button) dialog.findViewById(R.id.btZatwierdz);
                btZatwierdz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ContentValues values = new ContentValues();

                        if(nazwa.getText()== null || nazwa.getText().length() == 0){
                            nazwa.setTextColor(Color.RED);
                            nazwa.setTypeface(null, Typeface.BOLD);
                            nazwa.setText("Wpisz nazwę!");
                        }else{
                            Baza baza = new Baza(ListActivity.this);
                            EditText ilosc = (EditText) dialog.findViewById(R.id.etIlosc);
                            EditText cena = (EditText) dialog.findViewById(R.id.etCena);
                            CheckBox kupiono = (CheckBox) dialog.findViewById(R.id.cbKupione);
                            Product product = new Product(nazwa.getText().toString(),kupiono.isChecked() == false? 0:1);
                            if(ilosc.getText().length() == 0){
                                product.setIlosc(1);
                            }else{
                                product.setIlosc(Integer.parseInt(ilosc.getText().toString()));
                            }
                            if(cena.getText().length() == 0){
                                product.setCena(0);
                            }else{
                                product.setCena(Double.parseDouble(cena.getText().toString()));
                            }
                            //baza.dodaj(product);
                            values.put(ProductProvider.NAZWA, product.getNazwa());
                            values.put(ProductProvider.CENA, product.getCena() * 100);
                            values.put(ProductProvider.ILOSC, product.getIlosc());
                            values.put(ProductProvider.KUPIONO, product.getKupiono());
                            getContentResolver().insert(
                                    ProductProvider.CONTENT_URI, values);
                            zbudujListe();
                            dialog.dismiss();
                         }
                    }
                });
                dialog.show();
            }
        });
    }

    private  void zbudujListe(){
        //Baza baza = new Baza(this);
        //CustomAdapter ca = new CustomAdapter(this, baza.getAllProducts());
        String URL = "content://dajana.listazakupow.ProductsProvider";
        String[] kolumny = {"id","nazwa","cena","ilosc","kupiono"};
        Uri products = Uri.parse(URL);
        Cursor kursor = getContentResolver().query(products,kolumny,null,null,"kupiono, nazwa");// managedQuery(products, null, null, null, "name");
        ArrayList<Product> result = new ArrayList<Product>();
        kursor.moveToFirst();
        while(kursor.moveToNext()){
            Product product = new Product(kursor.getInt(0),kursor.getString(1), kursor.getInt(2), kursor.getInt(3),kursor.getInt(4));
            result.add(product);
        }

        CustomAdapter ca = new CustomAdapter(this, result);
        lvProducts.setAdapter(ca);


    }
}
