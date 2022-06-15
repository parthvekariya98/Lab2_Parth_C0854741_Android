package com.example.lab2_parth_c0854741_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        //get previous details for product
        String productId = getIntent().getStringExtra("PRODUCT_ID");
        String preProductName = getIntent().getStringExtra("PRODUCT_NAME");
        String preProductDescription = getIntent().getStringExtra("PRODUCT_DESC");
        String preProductPrice = getIntent().getStringExtra("PRODUCT_PRICE");

        //set previous product id
        TextView productIDText = findViewById(R.id.update_product_id);
        productIDText.setText("Product ID: " + productId);

        //set previous product name
        EditText productNameEditText = findViewById(R.id.et_update_product_name);
        productNameEditText.setText(preProductName);

        //set previous product description
        EditText productDescriptionEditText = findViewById(R.id.et_update_product_description);
        productDescriptionEditText.setText(preProductDescription);

        //set previous product price
        EditText productPriceEditText = findViewById(R.id.et_update_product_price);
        productPriceEditText.setText(preProductPrice);

        Button updateProductButton = findViewById(R.id.btn_update_product);

        //update button listener
        updateProductButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //get current values of product
                String productName = productNameEditText.getText().toString();
                String productDescription = productDescriptionEditText.getText().toString();
                String productPrice = productPriceEditText.getText().toString();

                //check for empty values
                if (!productName.isEmpty() && !productDescription.isEmpty() && !productPrice.isEmpty()) {
                    DBHelper dbHandler = new DBHelper(UpdateProductActivity.this);
                    //update database values with new values
                    dbHandler.UpdateProductDetails(
                            productId,
                            productNameEditText.getText().toString(),
                            productDescriptionEditText.getText().toString(),
                            productPriceEditText.getText().toString()
                    );
                    //success toast
                    Toast.makeText(getApplicationContext(), "Product Updated Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all values properly!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}