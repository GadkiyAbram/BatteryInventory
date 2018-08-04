package com.example.aleksandrabramovski.database;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowInRecyclerView extends AppCompatActivity implements View.OnClickListener{
    Button buttonToMainScreen, btnLoadToRV;
    EditText searchTxt;
    public LinearLayout linLayoutrecView;
    public final String LOG_TAG = "mylogs";
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeControlClass controlClass = null;

    RecyclerAdapter myAdapter;
    ArrayList<BatteryDetails> battery = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mRecyclerView = (RecyclerView)findViewById(R.id.batteryRecyclerView);           //Initialize RecyclerView
        setupRecyclerView();

        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerView);
        btnLoadToRV = (Button)findViewById(R.id.search);
        searchTxt = (EditText)findViewById(R.id.editTextSearch);
        btnLoadToRV.setOnClickListener(this);
        buttonToMainScreen = (Button)findViewById(R.id.buttonToMainScreen);
        buttonToMainScreen.setOnClickListener(this);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new RecyclerAdapter(this, battery, new RVClickListener() {
            @Override
            public void onItemClick(View v, int postition) {
                Log.d(LOG_TAG, "selected: " + battery.get(postition).getSerNum1());
            }
        });
    }

    public void retrieveAllData(String search){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();
        battery.clear();
        Cursor c;
        if (search != null){
            c = dbHelper.getSearchData(search);
        }else{
            c = dbHelper.getAllData();
        }
        while (c.moveToNext()){
            int _id = c.getInt(0);
            String boxNum = c.getString(1);
            String condition = c.getString(2);
            String serNum1 = c.getString(3);
            String dom = c.getString(6);
            battery.add(new BatteryDetails(_id, boxNum, serNum1, "",
                    "", dom, condition, "",""));
            Log.d(LOG_TAG, "battery: " + serNum1);
        }

        if (!(battery.size() < 1)){
            mRecyclerView.setAdapter(myAdapter);
        }else{
            Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v){
        String search = searchTxt.getText().toString();
        Intent intentToMainScreen;
        switch (v.getId()){
            case R.id.buttonToMainScreen:
                intentToMainScreen = new Intent(ShowInRecyclerView.this, StartActivity.class);
                startActivity(intentToMainScreen);
                break;
            case R.id.search:
                retrieveAllData(search);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView() {

        mRecyclerView.setAdapter(myAdapter);

        controlClass = new SwipeControlClass(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                myAdapter.battery.remove(position);
                myAdapter.notifyItemRemoved(position);
                myAdapter.notifyItemRangeChanged(position, myAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(controlClass);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                controlClass.onDraw(c);
            }
        });
    }
}
