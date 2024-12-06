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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etName, etDob;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etDob = findViewById(R.id.etDob);
        btnLogin = findViewById(R.id.btnLogin);

        // Initialize SharedPreferences and Gson
        sharedPreferences = getSharedPreferences("EmployeePrefs", Context.MODE_PRIVATE);
        gson = new Gson();

        // Set Login Button Click Listener
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

        // Retrieve list of staff members from SharedPreferences
        String json = sharedPreferences.getString("StaffList", "");
        Type type = new TypeToken<List<Staff>>() {}.getType();
        List<Staff> staffList;

        if (json.isEmpty()) {
            staffList = new ArrayList<>();
        } else {
            staffList = gson.fromJson(json, type);
            staffList = gson.fromJson(json, type);
        }

        // Verify login credentials
        boolean isLoggedIn = false;
        for (Staff staff : staffList) {
            if (staff.getName().equals(nameInput) && staff.getDob().equals(dobInput)) {
                isLoggedIn = true;
                break;
            }
        }

        if (isLoggedIn) {
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
