package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edt_email , edt_pass;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_email = findViewById(R.id.login_email);
        edt_pass = findViewById(R.id.login_pass);
        db = new DatabaseHelper(this);

        //SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        //long total = preference.getLong("total" , 0);
        //Log.d("here", "onCreate: total : " + total);
        //String val = String.valueOf(total);
        //tv_amount.setText(val);
    }

    /*public void On_click_credit(View view) {

        String val = edt_value.getText().toString().trim();

        if(val.isEmpty()) return;

        int res = db.credit_or_debit(val , 1 , this);
        if(res == 0) Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        long total = preference.getLong("total" , 0);
        Log.d("here", "onCreate: total : " + total);
        val = String.valueOf(total);
        tv_amount.setText(val);
        edt_value.setText("");

        // To clear the total i.e make total(balance) = 0
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Log.d("here", "credit_or_debit: inside");

        editor.clear();
        editor.apply();
    }

    public void On_click_debit(View view) {

        String val = edt_value.getText().toString().trim();
        if(val.isEmpty()) return;
        int res = db.credit_or_debit(val , 0 , this);

        if(res == 0) Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        long total = preference.getLong("total" , 0);
        Log.d("here", "onCreate: total : " + total);
        val = String.valueOf(total);
        tv_amount.setText(val);
        edt_value.setText("");
    }*/

    public void On_Login_Click(View view) {
        String email = edt_email.getText().toString();
        String password = edt_pass.getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        Log.d("here", "On_Login_Click: above result");
        int result = db.check(email , password);

        Log.d("here", "On_Login_Click:below result : " + result);

        if(result == 0) {
            Toast.makeText(this, "Invalid username/Password", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Successfull login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this , Credit_Debit.class);
            intent.putExtra("user" , email);
            startActivity(intent);
        }
    }

    public void Go_to_register(View view) {
        Intent intent = new Intent(this , Register.class);
        startActivity(intent);
    }


}