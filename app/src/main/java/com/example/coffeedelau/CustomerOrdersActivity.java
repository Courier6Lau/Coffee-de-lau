package com.example.coffeedelau;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class CustomerOrdersActivity extends AppCompatActivity {

    private TextView tvCustomerOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        tvCustomerOrders = findViewById(R.id.tvCustomerOrders);

        // Retrieve orders from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Unknown");

        // Assuming order details are saved as HashMap-like strings
        String orders = sharedPreferences.getString("CustomerOrders", "No orders found");

        // Display customer orders
        String orderDetails = "Customer Name: " + customerName + "\n" + "Orders:\n" + orders;
        tvCustomerOrders.setText(orderDetails);
    }
}
