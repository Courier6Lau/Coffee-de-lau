package com.example.coffeedelau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etName, etDob;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etDob = findViewById(R.id.etDob); // Corrected the ID to reflect Date of Birth instead of a password
        btnLogin = findViewById(R.id.btnLogin);

        // Set Log In Button Click Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyLogin();
            }
        });
    }

    private void verifyLogin() {
        // Get input values
        String nameInput = etName.getText().toString().trim();
        String dobInput = etDob.getText().toString().trim();

        if (nameInput.isEmpty() || dobInput.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both name and date of birth", Toast.LENGTH_SHORT).show();
            return;
        }

        // Access SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("EmployeePrefs", Context.MODE_PRIVATE);

        // Retrieve saved name and date of birth from SharedPreferences
        String savedName = sharedPreferences.getString("Name", null);
        String savedDob = sharedPreferences.getString("Dob", null);

        // Verify login credentials
        if (nameInput.equals(savedName) && dobInput.equals(savedDob)) {
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

            // Navigate to the AttendanceActivity
            Intent intent = new Intent(LoginActivity.this, AttendanceActivity.class);
            startActivity(intent);
            finish(); // Close current activity to prevent going back to login page after login
        } else {
            Toast.makeText(LoginActivity.this, "Invalid name or date of birth", Toast.LENGTH_SHORT).show();
        }
    }
}
