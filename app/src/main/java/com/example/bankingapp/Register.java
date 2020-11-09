package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText edt_fname , edt_lname , edt_email , edt_pass , edt_repass;
    Button btn_submit;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_fname = findViewById(R.id.reg_firstname);
        edt_lname = findViewById(R.id.reg_lastname);
        edt_email = findViewById(R.id.reg_email);
        edt_pass = findViewById(R.id.reg_password);
        edt_repass = findViewById(R.id.reg_reenterpassword);

        db = new DatabaseHelper(this);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void On_Register_click(View view) {
        String p1 = edt_pass.getText().toString().trim();
        String p2 = edt_repass.getText().toString().trim();
        Log.d("here", "On_Register_click: : " + p1 + "  " + p2);
        if(p1.equals(p2)) {

            String fname = edt_fname.getText().toString();
            String lname = edt_lname.getText().toString();
            String email = edt_email.getText().toString();
            String password = edt_pass.getText().toString();

            if(!isEmailValid(email)) {
                Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = db.insert(fname , lname , email , password);
            //int result = 1;
            if(result == 0) {
                Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);

            return;
        }

        Toast.makeText(this, "Password doesn't Match..Re-enter Password", Toast.LENGTH_SHORT).show();
        return;
    }
}