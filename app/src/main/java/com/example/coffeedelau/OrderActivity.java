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

public class OrderActivity extends AppCompatActivity {

    private EditText etOrderName;
    private Button btnSubmitOrder;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize views
        etOrderName = findViewById(R.id.etOrderName);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        // Initialize SharedPreferences and Gson
        sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
        gson = new Gson();

        // Set Submit Order Button Click Listener
        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
    }

    private void submitOrder() {
        // Get entered name
        String customerName = etOrderName.getText().toString().trim();

        if (customerName.isEmpty()) {
            Toast.makeText(OrderActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve existing orders from SharedPreferences
        String json = sharedPreferences.getString("CustomerOrdersList", "");
        Type type = new TypeToken<List<Order>>() {}.getType();
        List<Order> orders;

        if (json.isEmpty()) {
            orders = new ArrayList<>();
        } else {
            orders = gson.fromJson(json, type);
        }

        // Create an empty Order object for this customer (coffees will be added later in CatalogActivity)
        Order newOrder = new Order(customerName, "", 0);
        orders.add(newOrder);

        // Save updated orders list back to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String updatedJson = gson.toJson(orders);
        editor.putString("CustomerOrdersList", updatedJson);
        editor.apply();

        // Confirmation message
        Toast.makeText(OrderActivity.this, "Customer name saved! Proceed to select your coffee.", Toast.LENGTH_SHORT).show();

        // Redirect to CatalogActivity, passing the customer's name
        Intent intent = new Intent(OrderActivity.this, CatalogActivity.class);
        intent.putExtra("customerName", customerName);
        startActivity(intent);

        // Optional: Close OrderActivity to prevent navigating back
        finish();
    }
}
