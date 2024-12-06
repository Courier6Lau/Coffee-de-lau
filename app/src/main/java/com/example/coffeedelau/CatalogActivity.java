package com.example.coffeedelau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCoffeeCatalog;
    private CoffeeAdapter coffeeAdapter;
    private List<Coffee> coffeeList;
    private Button buttonConfirmOrder;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Initialize SharedPreferences and Gson
        sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
        gson = new Gson();

        recyclerViewCoffeeCatalog = findViewById(R.id.recyclerViewCoffeeCatalog);
        recyclerViewCoffeeCatalog.setLayoutManager(new GridLayoutManager(this, 2));

        coffeeList = new ArrayList<>();
        populateCoffeeList();

        coffeeAdapter = new CoffeeAdapter(this, coffeeList);
        recyclerViewCoffeeCatalog.setAdapter(coffeeAdapter);

        // Initialize and set up Confirm Order button
        buttonConfirmOrder = findViewById(R.id.buttonConfirmOrder);
        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add coffee items to customer order and update SharedPreferences
                confirmOrder();
            }
        });
    }

    private void populateCoffeeList() {
        coffeeList.add(new Coffee(R.drawable.coffee1, "Café Cubano", 45.00));
        coffeeList.add(new Coffee(R.drawable.coffee2, "Indian filter coffee", 50.00));
        coffeeList.add(new Coffee(R.drawable.coffee3, "Espresso freddo", 55.00));
        coffeeList.add(new Coffee(R.drawable.coffee4, "Freddo cappuccino", 60.00));
        coffeeList.add(new Coffee(R.drawable.coffee5, "Cappuccino", 65.00));
        coffeeList.add(new Coffee(R.drawable.coffee6, "Ristretto", 50.00));
        coffeeList.add(new Coffee(R.drawable.coffee7, "Frappé coffee", 70.00));
        coffeeList.add(new Coffee(R.drawable.coffee8, "Vietnamese Iced Coffee", 75.00));
        coffeeList.add(new Coffee(R.drawable.coffee9, "Espresso", 55.00));
        coffeeList.add(new Coffee(R.drawable.coffee10, "Turkish coffee", 60.00));
        coffeeList.add(new Coffee(R.drawable.coffee11, "Cortado", 65.00));
        coffeeList.add(new Coffee(R.drawable.coffee12, "Flat White", 68.00));
    }

    private void confirmOrder() {
        // Retrieve the list of customer orders
        String json = sharedPreferences.getString("CustomerOrdersList", "");
        Type type = new TypeToken<List<Order>>() {}.getType();
        List<Order> orders;

        if (json.isEmpty()) {
            orders = new ArrayList<>();
        } else {
            orders = gson.fromJson(json, type);
        }

        // Retrieve the customer's name from Intent
        String customerName = getIntent().getStringExtra("customerName");

        // Add current coffee selections to the orders list
        for (int i = 0; i < coffeeList.size(); i++) {
            int quantity = coffeeAdapter.getOrderCount(i);
            if (quantity > 0) {
                Coffee coffee = coffeeList.get(i);
                Order newOrder = new Order(customerName, coffee.getName(), quantity);
                orders.add(newOrder);
            }
        }

        // Save the updated orders list to SharedPreferences
        String updatedJson = gson.toJson(orders);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CustomerOrdersList", updatedJson);
        editor.apply();

        // Redirect to OrderConfirmationActivity
        Intent intent = new Intent(CatalogActivity.this, OrderConfirmationActivity.class);
        startActivity(intent);
    }
}
