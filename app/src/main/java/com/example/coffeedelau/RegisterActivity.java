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

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etAge, etDob, etAddress;
    private Button btnRegister;
    private SharedPreferences sharedPreferences;
    private Gson gson;

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

        // Initialize SharedPreferences and Gson
        sharedPreferences = getSharedPreferences("EmployeePrefs", Context.MODE_PRIVATE);
        gson = new Gson();

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

        // Create a Staff object to save the data
        Staff newStaff = new Staff(name, age, dob, address);

        // Retrieve the existing list of staff members
        String json = sharedPreferences.getString("StaffList", "");
        Type type = new TypeToken<List<Staff>>() {}.getType();
        List<Staff> staffList;

        if (json.isEmpty()) {
            staffList = new ArrayList<>();
        } else {
            staffList = gson.fromJson(json, type);
        }

        // Add the new staff to the list
        staffList.add(newStaff);

        // Save updated staff list back to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String updatedJson = gson.toJson(staffList);
        editor.putString("StaffList", updatedJson);
        editor.apply();

        // Confirmation message
        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

        // Clear the input fields
        etName.setText("");
        etAge.setText("");
        etDob.setText("");
        etAddress.setText("");

        // Redirect to LoginActivity (Optional)
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

        // Optional: Close RegisterActivity to prevent navigating back
        finish();
    }
}
