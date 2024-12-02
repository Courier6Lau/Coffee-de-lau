package com.example.coffeedelau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView textViewConfirmationMessage;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        textViewConfirmationMessage = findViewById(R.id.textViewConfirmationMessage);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Retrieve customer name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Customer");

        // Set confirmation message
        String confirmationMessage = "Thank you " + customerName + " for your order! Please wait for the barista to call you and for the total payment.";
        textViewConfirmationMessage.setText(confirmationMessage);

        // Set button click listener to go back to HomeActivity
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close confirmation activity to prevent going back to it
            }
        });
    }
}
