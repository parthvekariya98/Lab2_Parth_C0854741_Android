package com.example.lab2_parth_c0854741_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab2_parth_c0854741_android.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ProductsAdapter.ProductClickListener {
    private ActivityMainBinding binding;
    ProductsAdapter mAdapter;
    ArrayList<ProductModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setAddBtnListener();
        addCustomProductList();
        setProductList();
        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //to reload product list on re appearing the activity
        setProductList();
        setAdapter();
    }

    //add custom 10 product list
    private void addCustomProductList() {
        DBHelper dbHandler = new DBHelper(this);
        ArrayList<HashMap<String, String>> products_list = dbHandler.GetProducts();

        //check with product data base if empty then add static list
        if (products_list.size() == 0) {
            //insert new values to database
            dbHandler.insertProductDetails("PR01", "Apple iPad Mini 2", "These pre-owned products have been inspected and tested by Amazon-qualified suppliers, which typically perform a full diagnostic test, replacement of any defective parts, and a thorough cleaning process.", "189.99");
            dbHandler.insertProductDetails("PR02", "Acer Premium 15.6\"", "AMD Dual Core A6-9220C 1.8 GHZ Processor/4GB DDR4 Ram/ 32GB eMMC 15.6\" FULL HD (1920X1080) IPS TOUCH Screen / RADEON R5 VIDEO /Wide View HDR Webcam/ microSD card reader /NO Optical Drive", "569.99");
            dbHandler.insertProductDetails("PR03", "Apple iPhone XR", "6.1\" LCD Multi-Touch display with IPS technology, 828 x 1792 pixels, 19.5:9 ratio, upgradable to iOS 14.7, Bluetooth 5.0", "339.95");
            dbHandler.insertProductDetails("PR04", "Samsung Galaxy Watch Active2", "LTE bands: B2(1900), B4(AWS), B5(850), B12(700), B13(700), B17(700), B18(800), B19(800), B25(1900), B26(850), B66(AWS-3). Works on ALL CARRIERS (ATT, TMOBILE, VERIZON and Sprint)", "136.48");
            dbHandler.insertProductDetails("PR05", "Apple iPhone SE 2020", "This phone is unlocked and compatible with any carrier of choice on GSM and CDMA networks (e.g. AT&T, T-Mobile, Sprint, Verizon, US Cellular, Cricket, Metro, Tracfone, Mint Mobile, etc.).", "309.99");
            dbHandler.insertProductDetails("PR06", "SAMSUNG Galaxy Buds Pro", "Intelligent Active Noise Cancellation: Escape and tune in to your own moment of Zen — all with a single tap; Answer calls and instantly switch to talking with voice detection and let in the sounds that matter most with 4 ambient levels", "129.91");
            dbHandler.insertProductDetails("PR07", "Logitech G PRO X Headset", "Detachable pro grade microphone featuring real time Blue VO!CE technology,* including noise reducer, compressor, limiter and more for cleaner, professional sounding voice comms *Requires included USB external sound card, Windows PC, and Logitech G HUB software", "88.93");
            dbHandler.insertProductDetails("PR08", "Vitamix Explorian Blender", "Variable Speed Control: Ten variable speeds allow you to refine every texture with culinary precision, from the smoothest purées to the heartiest soups", "368.39");
            dbHandler.insertProductDetails("PR09", "Juice Fountain Plus", "This Certified Remanufactured product is tested and certified by Breville to look and work like-new. The product is backed by a 6 month warranty", "152.09");
            dbHandler.insertProductDetails("PR10", "Insignia Digital Air Fryer", "A healthier alternative Circulates hot air to cook food with little to no oil, resulting in less fat than in traditional fryers.", "149.99");
        }
    }

    //set product list from data base
    private void setProductList() {
        productList = new ArrayList<>();

        //get product list from database and set to productList
        DBHelper db = new DBHelper(this);
        ArrayList<HashMap<String, String>> products_list = db.GetProducts();

        if (products_list.size() > 0) {
            for (int i = 0; i < products_list.size(); i++) {
                HashMap<String, String> currentProduct = products_list.get(i);
                productList.add(new ProductModel(
                        currentProduct.get("product_id"),
                        currentProduct.get("product_name"),
                        currentProduct.get("product_description"),
                        currentProduct.get("product_price")));
            }
        }

    }

    //set product adapter method
    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new ProductsAdapter(this, this);
        }
        if (binding.list.getAdapter() == null) {
            binding.list.setLayoutManager(new LinearLayoutManager(this));
            binding.list.setHasFixedSize(false);
            binding.list.setAdapter(mAdapter);
        }

        mAdapter.doRefresh(productList);
    }

    //on delete btn tap listener
    @Override
    public void onDeleteClickListener(int position) {
        //check current position for product ID
        if (productList.size() > 0) {
            if (productList.get(position) != null) {
                ProductModel currentProduct = productList.get(position);
                DBHelper db = new DBHelper(this);

                //delete product with current product ID
                db.DeleteProduct(currentProduct.getProductId());
                Toast.makeText(this,"Product Deleted Successfully.",Toast.LENGTH_SHORT).show();

                //reload activity
                recreate();
            }
        }
    }

    @Override
    public void onUpdateClickListener(int position) {
        //check current position for product ID
        if (productList.size() > 0) {
            if (productList.get(position) != null) {
                ProductModel currentProduct = productList.get(position);
                //call update activity and pass current record data
                Intent intent = new Intent(getBaseContext(), UpdateProductActivity.class);
                intent.putExtra("PRODUCT_ID", currentProduct.getProductId());
                intent.putExtra("PRODUCT_NAME", currentProduct.getProductName());
                intent.putExtra("PRODUCT_DESC", currentProduct.getProductDescription());
                intent.putExtra("PRODUCT_PRICE", currentProduct.getProductPrice());
                startActivity(intent);
            }
        }
    }

    //call add product activity
    public void setAddBtnListener() {
        binding.btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddProductActivity.class));
        });
    }
}