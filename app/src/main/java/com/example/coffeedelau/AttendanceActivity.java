package com.example.coffeedelau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {

    private TextView tvWelcome, tvAttendanceStatus;
    private Button btnPunchIn, btnPunchOut, btnLogout, btnCustomerOrders;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Initialize Views
        tvWelcome = findViewById(R.id.tvWelcome);
        tvAttendanceStatus = findViewById(R.id.tvAttendanceStatus);
        btnPunchIn = findViewById(R.id.btnPunchIn);
        btnPunchOut = findViewById(R.id.btnPunchOut);
        btnLogout = findViewById(R.id.btnLogout);
        btnCustomerOrders = findViewById(R.id.btnCustomerOrders); // New button for customer orders

        // Access SharedPreferences
        sharedPreferences = getSharedPreferences("EmployeePrefs", Context.MODE_PRIVATE);
        String staffName = sharedPreferences.getString("Name", "Staff");

        // Set welcome message
        tvWelcome.setText("Welcome, " + staffName + "!");

        // Set Punch In Button Listener
        btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punchIn();
            }
        });

        // Set Punch Out Button Listener
        btnPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punchOut();
            }
        });

        // Set Logout Button Listener
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Set Customer Orders Button Listener
        btnCustomerOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCustomerOrders();
            }
        });
    }

    private void punchIn() {
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PunchInTime", currentTime);
        editor.apply();
        tvAttendanceStatus.setText("Punched In at: " + currentTime);
        Toast.makeText(AttendanceActivity.this, "You have punched in at " + currentTime, Toast.LENGTH_SHORT).show();
    }

    private void punchOut() {
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PunchOutTime", currentTime);
        editor.apply();
        tvAttendanceStatus.setText("Punched Out at: " + currentTime);
        Toast.makeText(AttendanceActivity.this, "You have punched out at " + currentTime, Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        // Navigate back to HomeActivity
        Intent intent = new Intent(AttendanceActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Close AttendanceActivity to prevent going back on logout
    }

    private void viewCustomerOrders() {
        // Navigate to CustomerOrdersActivity
        Intent intent = new Intent(AttendanceActivity.this, CustomerOrdersActivity.class);
        startActivity(intent);
    }
}
