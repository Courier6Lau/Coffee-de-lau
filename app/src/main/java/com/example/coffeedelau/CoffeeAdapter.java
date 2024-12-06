package com.example.coffeedelau;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    private Context context;
    private List<Coffee> coffeeList;
    private HashMap<Integer, Integer> orderCounts; // Track the count for each coffee item
    private Gson gson;

    public CoffeeAdapter(Context context, List<Coffee> coffeeList) {
        this.context = context;
        this.coffeeList = coffeeList;
        this.orderCounts = new HashMap<>(); // Initialize the HashMap
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coffee, parent, false);
        return new CoffeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);
        holder.imageView.setImageResource(coffee.getImageResource());
        holder.textViewName.setText(coffee.getName());
        holder.textViewPrice.setText("₱ " + coffee.getPrice());

        // Initialize order count for this item if not already initialized
        if (!orderCounts.containsKey(position)) {
            orderCounts.put(position, 0); // Initialize with 0
        }

        // Set the current order count
        holder.textViewOrderCount.setText(String.valueOf(orderCounts.get(position)));

        // Set click listener to increase order count
        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase the count for this coffee item
                int currentCount = orderCounts.get(position);
                currentCount++;
                orderCounts.put(position, currentCount);

                // Update the displayed count
                holder.textViewOrderCount.setText(String.valueOf(currentCount));

                // Save to SharedPreferences
                saveOrderToPreferences();
            }
        });

        // Set click listener to decrease order count
        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Decrease the count for this coffee item if it's above zero
                int currentCount = orderCounts.get(position);
                if (currentCount > 0) {
                    currentCount--;
                    orderCounts.put(position, currentCount);

                    // Update the displayed count
                    holder.textViewOrderCount.setText(String.valueOf(currentCount));

                    // Save to SharedPreferences
                    saveOrderToPreferences();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }

    private void saveOrderToPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve the customer's name from SharedPreferences
        String customerName = sharedPreferences.getString("CustomerName", "Anonymous");

        // Prepare the list of orders
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < coffeeList.size(); i++) {
            if (orderCounts.get(i) != null && orderCounts.get(i) > 0) {
                Coffee coffee = coffeeList.get(i);
                Order order = new Order(customerName, coffee.getName(), orderCounts.get(i));
                orders.add(order);
            }
        }

        // Convert list of orders to JSON string and save to SharedPreferences
        String ordersJson = gson.toJson(orders);
        editor.putString("CustomerOrders", ordersJson);
        editor.apply();
    }

    public int getOrderCount(int position) {
        // Return the order count for the specified coffee item
        return orderCounts.getOrDefault(position, 0);
    }

    public static class CoffeeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName, textViewPrice, textViewOrderCount;
        Button buttonAdd, buttonRemove;

        public CoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCoffee);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewOrderCount = itemView.findViewById(R.id.textViewOrderCount);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
            buttonRemove = itemView.findViewById(R.id.buttonRemove); // Initialize the remove button
        }
    }
}
