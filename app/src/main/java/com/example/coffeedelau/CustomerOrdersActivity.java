package com.example.coffeedelau;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CustomerOrdersActivity extends AppCompatActivity {

    private TextView tvCustomerOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        tvCustomerOrders = findViewById(R.id.tvCustomerOrders);

        try {
            // Retrieve orders from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
            String ordersJson = sharedPreferences.getString("CustomerOrdersList", "[]");

            // Convert the orders JSON string to a List<Order> using Gson
            Gson gson = new Gson();
            Type type = new TypeToken<List<Order>>() {}.getType();
            List<Order> customerOrders = gson.fromJson(ordersJson, type);

            if (customerOrders == null || customerOrders.isEmpty()) {
                tvCustomerOrders.setText("No orders found.\n");
                return;
            }

            // Build the order details string in a simple list format
            StringBuilder orderDetailsBuilder = new StringBuilder();
            orderDetailsBuilder.append("All Customer Orders:\n\n");

            for (Order order : customerOrders) {
                orderDetailsBuilder
                        .append(order.getCustomerName()).append(" ")
                        .append(order.getCoffeeName()).append(" ")
                        .append(order.getQuantity()).append("\n");
            }

            // Display customer orders
            tvCustomerOrders.setText(orderDetailsBuilder.toString());

        } catch (Exception e) {
            Log.e("CustomerOrdersActivity", "Error retrieving orders: ", e);
            tvCustomerOrders.setText("An error occurred while loading orders.");
        }
    }
}
