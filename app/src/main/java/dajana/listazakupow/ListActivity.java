package dajana.listazakupow;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ListActivity extends AppCompatActivity {
    final Context context =  this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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
                        }
                    }
                });
                Button btZatwierdz = (Button) dialog.findViewById(R.id.btZatwierdz);
                btZatwierdz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nazwa.getText()== null || nazwa.getText().length() == 0){
                            nazwa.setTextColor(Color.RED);
                            nazwa.setTypeface(null, Typeface.BOLD);
                            nazwa.setText("Wpisz nazwę!!");
                        }else{
                            EditText ilosc = (EditText) dialog.findViewById(R.id.etIlosc);
                            EditText cena = (EditText) dialog.findViewById(R.id.etCena);
                            CheckBox kupiono = (CheckBox) dialog.findViewById(R.id.cbKupione);
                            if(ilosc.getText().length() == 0){

                            }else{

                            }
                            if(cena.getText().length() == 0){

                            }else{

                            }
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private  void zbudujListe(){

    }
}
