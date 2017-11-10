package dajana.listazakupow;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {
    private Spinner spinCzcionka;
    private Spinner spinKolor;
    private TextView tvCzcionka;
    private TextView tvKolor;
    public final static Float FONT_MIN = 15.5f;
    public final static Float FONT_MED = 17.5f;
    public final static Float FONT_MAX = 20f;
    public final static int BLACK = new Color().BLACK;
    public final static int RED = new Color().RED;
    public final static int GREEN = new Color().GREEN;
    public final static int BLUE = new Color().BLUE;
    private Boolean isInitFont = true;
    private Boolean isInitColor = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinCzcionka = (Spinner) findViewById(R.id.spinnerFontSize);
        spinKolor =(Spinner) findViewById(R.id.spinnerFontColor);
        tvCzcionka = (TextView) findViewById(R.id.tvCzcionka);
        tvKolor = (TextView) findViewById(R.id.tvKolor);
        SharedPreferences settings = getSharedPreferences("font_size", 0);
        UpdateFont(settings.getFloat("size",FONT_MED));
        UpdateFontSppinerValue(settings.getFloat("size",FONT_MED));
        settings = getSharedPreferences("font_color", 0);
        UpdateFontColor(settings.getInt("color",BLACK));
        UpdateColorSppinerValue(settings.getInt("color",BLACK));

        spinCzcionka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!isInitFont) {
                    float fontSize = 0;
                    SharedPreferences settings = getSharedPreferences("font_size", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    switch (position) {
                        case 0:
                            fontSize = FONT_MIN;
                            break;
                        case 1:
                            fontSize = FONT_MED;
                            break;
                        case 2:
                            fontSize = FONT_MAX;
                            break;
                    }
                    editor.putFloat("size", fontSize);
                    UpdateFont(fontSize);
                    editor.commit();
                }
                isInitFont=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinKolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!isInitColor) {
                    int fontColor = 0;
                    SharedPreferences settings = getSharedPreferences("font_color", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    switch (position) {
                        case 0:
                            fontColor = BLACK;
                            break;
                        case 1:
                            fontColor = RED;
                            break;
                        case 2:
                            fontColor = GREEN;
                            break;
                        case 3:
                            fontColor = BLUE;
                            break;
                    }
                    editor.putInt("color", fontColor);
                    UpdateFontColor(fontColor);
                    editor.commit();
                }
                isInitColor=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }
    private void UpdateFontSppinerValue(float size) {
        if(size == FONT_MIN){
            spinCzcionka.setSelection(0);
        }
        else if(size == FONT_MED){
            spinCzcionka.setSelection(1);
        }
        else {
            spinCzcionka.setSelection(2);
        }
    }
    private void UpdateColorSppinerValue(int color) {
        if(color == BLACK){
            spinKolor.setSelection(0);
        }
        else if(color == RED){
            spinKolor.setSelection(1);
        }
        else if(color == GREEN){
            spinKolor.setSelection(2);
        }else{
            spinKolor.setSelection(3);
        }
    }

    private void UpdateFont(float size){
        tvCzcionka.setTextSize(size);
        tvKolor.setTextSize(size);
    }
    private void UpdateFontColor(int color){
        tvCzcionka.setTextColor(color);
        tvKolor.setTextColor(color);
    }
}
