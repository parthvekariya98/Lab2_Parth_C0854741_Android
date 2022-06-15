package com.example.lab2_parth_c0854741_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        //bind views with ids
        EditText productIDEditText = findViewById(R.id.et_product_id);
        EditText productNameEditText = findViewById(R.id.et_product_name);
        EditText productDescriptionEditText = findViewById(R.id.et_product_description);
        EditText productPriceEditText = findViewById(R.id.et_product_price);
        Button addProductButton = findViewById(R.id.btn_add_product);

        //add button click listener
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //get current values of product
                String productId = productIDEditText.getText().toString();
                String productName = productNameEditText.getText().toString();
                String productDescription = productDescriptionEditText.getText().toString();
                String productPrice = productPriceEditText.getText().toString();

                //check for empty values
                if (!productId.isEmpty() && !productName.isEmpty() && !productDescription.isEmpty()
                        && !productPrice.isEmpty()) {
                    DBHelper dbHandler = new DBHelper(AddProductActivity.this);
                    //insert new values to database
                    dbHandler.insertProductDetails(
                            productIDEditText.getText().toString(),
                            productNameEditText.getText().toString(),
                            productDescriptionEditText.getText().toString(),
                            productPriceEditText.getText().toString()
                    );
                    //success toast
                    Toast.makeText(getApplicationContext(), "Product Added Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all values properly!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}