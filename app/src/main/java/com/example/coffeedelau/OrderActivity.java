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

public class OrderActivity extends AppCompatActivity {

    private EditText etOrderName;
    private Button btnSubmitOrder;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize views
        etOrderName = findViewById(R.id.etOrderName);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);

        // Set Submit Button Click Listener
        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
    }

    private void submitOrder() {
        // Get entered name
        String orderName = etOrderName.getText().toString().trim();

        if (orderName.isEmpty()) {
            Toast.makeText(OrderActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save name in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CustomerName", orderName);
        editor.putString("CustomerOrders", ""); // Initialize with empty orders list
        editor.apply();

        // Confirmation message
        Toast.makeText(OrderActivity.this, "Name Submitted!", Toast.LENGTH_SHORT).show();

        // Redirect to CatalogActivity
        Intent intent = new Intent(OrderActivity.this, CatalogActivity.class);
        startActivity(intent);

        // Clear the input field
        etOrderName.setText("");

        // Optional: Close OrderActivity to prevent navigating back
        finish();
    }
}
