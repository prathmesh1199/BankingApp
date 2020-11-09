package com.example.bankingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Credit_Debit extends AppCompatActivity {

    EditText edt_value , edt_acc;
    Button btn_credit , btn_debit;
    TextView tv_amount , tv_balance;
    DatabaseHelper db;
    long curr_total = 0;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit__debit);

        Intent i = getIntent();
        user = i.getStringExtra("user");

        Log.d("here", "onCreate: user : " + user);

        edt_value = findViewById(R.id.edt_value);
        btn_credit = findViewById(R.id.btn_credit);
        btn_debit = findViewById(R.id.btn_debit);
        tv_amount = findViewById(R.id.tv_amount);


        db = new DatabaseHelper(this);

        Log.d("here", "onCreate: before balance : " + curr_total);

        curr_total = db.get_balance(user);

        Log.d("here", "onCreate: balance : " + curr_total);


        //Log.d("here", "onCreate: val : " + val);
        tv_amount.setText(String.valueOf(curr_total));

        //Log.d("here", "onCreate: val : " + val);

    }

    public void On_click_credit(View view) {

        String val = edt_value.getText().toString().trim();



        if(val.isEmpty()) {
            Toast.makeText(this, "Enter valid value", Toast.LENGTH_SHORT).show();
            return;
        }

        long res = db.credit_or_debit(val , 1 , this , curr_total , user);

        if(res == -1) Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
            tv_amount.setText(String.valueOf(res));
            curr_total = res;
        }

        edt_value.setText("");
    }

    public void On_click_debit(View view) {

        String val = edt_value.getText().toString().trim();

        if(val.isEmpty()) {
            Toast.makeText(this, "Enter valid value", Toast.LENGTH_SHORT).show();
            return;
        }

        long res = db.credit_or_debit(val , 0 , this , curr_total , user);
        if(res == -1) Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
            curr_total = res;
            tv_amount.setText(String.valueOf(res));
        }

        edt_value.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_logout :
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}