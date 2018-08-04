package com.example.aleksandrabramovski.database;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr.Abramovski on 19/06/2017.
 */
public class ShowBatteriesActivity extends AppCompatActivity {
    final String LOG_TAG = "mylogs";
    Button buttonToMainScreen, buttonSearch;
    EditText edtSearch;
    DatabaseHelper myDb;
    public LinearLayout linLayout;
    public String etSearch;
    public int[] colors = new int[2];
    public ArrayList<String> arrTemp = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_batteries_activity);
        Log.d(LOG_TAG, "ShowBatteryActivity created");
        edtSearch = (EditText)findViewById(R.id.editTextSearch);
        buttonToMainScreen = (Button)findViewById(R.id.buttonToMainScreen);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);
        edtSearch = (EditText)findViewById(R.id.editTextSearch);
        linLayout = (LinearLayout) findViewById(R.id.linLayout);
        myDb = new DatabaseHelper(this);
        showSearchData();
        colors[0] = Color.parseColor("#00E676");
        colors[1] = Color.parseColor("#FFEE58");
    }

    public void backToMainScreen(View view){
        Intent intentMain = new Intent(ShowBatteriesActivity.this, StartActivity.class);
        startActivity(intentMain);
    }

    public void showSearchData(){
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linLayout.removeAllViews();
                Cursor res;
                LayoutInflater inflater = getLayoutInflater();

                etSearch = edtSearch.getText().toString();
                if (etSearch.isEmpty()){
                    res = myDb.getAllData();
                }else{
                    res = myDb.getSearchData(etSearch);
                }
                while (res.moveToNext()) {
                    View item = inflater.inflate(R.layout.batteryitem, linLayout, false);
                    TextView tvSerial = (TextView) item.findViewById(R.id.tvSerialNumber1);
                    tvSerial.setText(res.getString(2));
                    arrTemp.add(res.getString(res.getColumnIndex("serNum1")));
                    Log.d(LOG_TAG, "Battery " + res.getString(res.getColumnIndex("serNum1")) +
                            "added to ArrayList arrTemp");
                    TextView tvBoxN = (TextView) item.findViewById(R.id.tvBoxN);
                    tvBoxN.setText("Box №: " + res.getString(0));
                    TextView tvDateM = (TextView) item.findViewById(R.id.tvDateManuf);
                    tvDateM.setText("Manuf: " + res.getString(5));
                    TextView tvCond = (TextView) item.findViewById(R.id.tvCondition);
                    tvCond.setText(res.getString(1));
                    if (res.getString(1).trim().length() == 3){
                        item.setBackgroundColor(colors[0]);
                    }else{
                        item.setBackgroundColor(colors[1]);
                    }
                    item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                    linLayout.addView(item);
                }
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
