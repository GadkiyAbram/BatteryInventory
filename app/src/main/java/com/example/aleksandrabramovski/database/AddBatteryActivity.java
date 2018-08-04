package com.example.aleksandrabramovski.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

public class AddBatteryActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editTextBoxN, editTextSer1, editTextSer2, editTextSer3, editTextDM, editTextCont, editTextComment;
    RadioButton radioButtonNB, radioButtonUB;
    Button buttonShowBatteries;
    DatabaseHelper myDb;
    String cond = "";
    SwipeButton swipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_battery_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        myDb = new DatabaseHelper(this);
        editTextBoxN = (EditText)findViewById(R.id.editTextBoxN);
        radioButtonNB = (RadioButton)findViewById(R.id.radioButtonNB);
        radioButtonUB = (RadioButton)findViewById(R.id.radioButtonUB);
        editTextSer1 = (EditText)findViewById(R.id.editTextSer1);
        editTextSer2 = (EditText)findViewById(R.id.editTextSer2);
        editTextSer3 = (EditText)findViewById(R.id.editTextSer3);
        editTextDM = (EditText)findViewById(R.id.editTextDM);
        editTextCont = (EditText)findViewById(R.id.editTextCont);
        editTextComment = (EditText)findViewById(R.id.editTextComment);

        buttonShowBatteries = (Button)findViewById(R.id.buttonShowBatteries);
        buttonShowBatteries.setOnClickListener(this);

        swipeButton = (SwipeButton)findViewById(R.id.swipeBtn);
        swipeButton.setBackgroundColor(192192192);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                AddData();
            }
        });
    }

    public void AddData(){
        String cond = "";
        if (radioButtonNB.isChecked()){
            cond = "New";
        }else if(radioButtonUB.isChecked()){
            cond = "Used";
        }
        boolean resultCheck = myDb.checkIfBattExists(editTextSer1.getText().toString());
        if (editTextSer1.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "At least serial number need...", Toast.LENGTH_SHORT).show();
        }else if (resultCheck == true){
            Toast.makeText(getApplicationContext(), "Battery with S/N " +
                    editTextSer1.getText().toString().toUpperCase() + " exists", Toast.LENGTH_SHORT).show();
        }else {
            boolean isInserted = myDb.insertData(
                    editTextBoxN.getText().toString(),
                    cond, editTextSer1.getText().toString().toUpperCase(),
                    editTextSer2.getText().toString().toUpperCase(),
                    editTextSer3.getText().toString().toUpperCase(),
                    editTextDM.getText().toString(),
                    editTextCont.getText().toString().toUpperCase(),
                    editTextComment.getText().toString());
            if(isInserted == true){
                Toast.makeText(AddBatteryActivity.this, "Battery " + editTextSer1.getText().toString() + " Inserted", Toast.LENGTH_LONG).show();
                editTextBoxN.setText("");
                editTextSer1.setText("");
                editTextSer2.setText("");
                editTextSer3.setText("");
                editTextDM.setText("");
                editTextCont.setText("");
                editTextComment.setText("");
            }else {
                Toast.makeText(AddBatteryActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.buttonShowBatteries:
                intent = new Intent(AddBatteryActivity.this, ShowBatteriesActivity.class);
                startActivity(intent);
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
}

