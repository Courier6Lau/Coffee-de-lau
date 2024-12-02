package com.example.coffeedelau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCoffeeCatalog;
    private CoffeeAdapter coffeeAdapter;
    private List<Coffee> coffeeList;
    private Button buttonConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

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
                // Navigate to OrderConfirmationActivity
                Intent intent = new Intent(CatalogActivity.this, OrderConfirmationActivity.class);
                startActivity(intent);
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
}
