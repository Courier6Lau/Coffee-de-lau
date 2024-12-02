package com.example.coffeedelau;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etAge, etDob, etAddress;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etDob = findViewById(R.id.etDob);
        etAddress = findViewById(R.id.etAddress);
        btnRegister = findViewById(R.id.btnRegister);

        // Set Register Button Click Listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmployeeData();
            }
        });
    }

    private void saveEmployeeData() {
        // Get input values
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (name.isEmpty() || age.isEmpty() || dob.isEmpty() || address.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("EmployeePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Name", name);
        editor.putString("Age", age);
        editor.putString("Dob", dob);
        editor.putString("Address", address);
        editor.apply();

        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

        // Clear the input fields
        etName.setText("");
        etAge.setText("");
        etDob.setText("");
        etAddress.setText("");
    }
}
