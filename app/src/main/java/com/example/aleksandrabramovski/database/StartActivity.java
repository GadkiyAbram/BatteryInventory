package com.example.aleksandrabramovski.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Aleksandr.Abramovski on 19/06/2017.
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    Button ButtonAddBattery, ButtonShowBatteries, ButtonRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_start_activity);

        //buttons initialization
        ButtonAddBattery = (Button)findViewById(R.id.buttonAddScreen);
        ButtonShowBatteries = (Button)findViewById(R.id.buttonShowBatteries);
        ButtonRecyclerView = (Button)findViewById(R.id.buttonRecyclerV);

        //button's event initialization
        ButtonAddBattery.setOnClickListener(this);
        ButtonShowBatteries.setOnClickListener(this);
        ButtonRecyclerView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        Intent intentSelection;
        switch (v.getId()){
            case R.id.buttonAddScreen:
                intentSelection = new Intent(StartActivity.this, AddBatteryActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.buttonShowBatteries:
                intentSelection = new Intent(StartActivity.this, ShowBatteriesActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.buttonRecyclerV:
                intentSelection = new Intent(StartActivity.this, ShowInRecyclerView.class);
                startActivity(intentSelection);
                break;
        }
    }
}
