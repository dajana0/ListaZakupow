package adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import dajana.listazakupow.R;
import dajana.listazakupow.SettingsActivity;
import models.Product;

/**
 * Created by Dajana on 12.11.2017.
 */

public class CustomAdapter extends BaseAdapter
{
    ArrayList<Product> productsList;
    Context mContext;


    public CustomAdapter(Context context, ArrayList<Product> productsList) {
        this.mContext = context;
        this.productsList = productsList;

    }

    @Override
    public int getCount() {
        return productsList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = productsList.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.product_item,null);
        SharedPreferences settingsSize = mContext.getSharedPreferences("font_size", 0);
        SharedPreferences settingsColor = mContext.getSharedPreferences("font_color", 0);
        TextView tvNazwa = (TextView) convertView.findViewById(R.id.nazwa);
        tvNazwa.setTextColor(settingsColor.getInt("color", SettingsActivity.BLACK));
        tvNazwa.setTextSize(settingsSize.getFloat("size", SettingsActivity.FONT_MED));

        TextView tvCena = (TextView) convertView.findViewById(R.id.cena);
        tvCena.setTextColor(settingsColor.getInt("color", SettingsActivity.BLACK));
        tvCena.setTextSize(settingsSize.getFloat("size", SettingsActivity.FONT_MED));

        TextView tvIlosc = (TextView) convertView.findViewById(R.id.ilosc);
        tvIlosc.setTextColor(settingsColor.getInt("color", SettingsActivity.BLACK));
        tvIlosc.setTextSize(settingsSize.getFloat("size", SettingsActivity.FONT_MED));

        CheckBox cbKupiono= (CheckBox) convertView.findViewById(R.id.kupiono);
        tvNazwa.setText(product.getNazwa());
        tvCena.setText(String.valueOf(product.getCena()));
        tvIlosc.setText(String.valueOf(product.getIlosc()));
        cbKupiono.setChecked(product.getKupiono() == 0?false:true);
        return convertView;
    }
}