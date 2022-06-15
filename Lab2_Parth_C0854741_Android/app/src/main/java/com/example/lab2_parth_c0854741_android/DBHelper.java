package com.example.lab2_parth_c0854741_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PRODUCTS_DB";
    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRODUCT_DESCRIPTION = "product_description";
    private static final String KEY_PRODUCT_PRICE= "product_price";
    public DBHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRODUCT_ID + " TEXT,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PRODUCT_DESCRIPTION + " TEXT,"
                + KEY_PRODUCT_PRICE + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new Product Details
    void insertProductDetails(
            String product_id,
            String product_name, String product_description, String product_price){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues productValues = new ContentValues();
        productValues.put(KEY_PRODUCT_ID, product_id);
        productValues.put(KEY_PRODUCT_NAME, product_name);
        productValues.put(KEY_PRODUCT_DESCRIPTION, product_description);
        productValues.put(KEY_PRODUCT_PRICE, product_price);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_PRODUCTS,null, productValues);
        db.close();
    }

    // Get Product List
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetProducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT product_id, product_name, product_description, product_price FROM "+ TABLE_PRODUCTS;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> productObj = new HashMap<>();
            productObj.put("product_id",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_ID)));
            productObj.put("product_name",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME)));
            productObj.put("product_description",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_DESCRIPTION)));
            productObj.put("product_price",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_PRICE)));
            productList.add(productObj);
        }
        return  productList;
    }

    // Get Product Details based on productId
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetProductByProductId(String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT product_name, product_description, product_price FROM "+ TABLE_PRODUCTS;
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{KEY_PRODUCT_ID, KEY_PRODUCT_NAME, KEY_PRODUCT_DESCRIPTION, KEY_PRODUCT_PRICE},
                KEY_PRODUCT_ID+ "=?",new String[]{String.valueOf(productId)},
                null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> productObj = new HashMap<>();
            productObj.put("product_id",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_ID)));
            productObj.put("product_name",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME)));
            productObj.put("product_description",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_DESCRIPTION)));
            productObj.put("product_price",cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_PRICE)));
            productList.add(productObj);
        }
        return  productList;
    }

    // Delete Product Details
    public void DeleteProduct(String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_PRODUCT_ID+" = ?",new String[]{String.valueOf(productId)});
        db.close();
    }

    //Delete all product records
    public void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, null,null);
        db.close();
    }

    // Update Product Details
    public int UpdateProductDetails(String product_id, String product_name, String product_description, String product_price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues productValues = new ContentValues();
        productValues.put(KEY_PRODUCT_ID, product_id);
        productValues.put(KEY_PRODUCT_NAME, product_name);
        productValues.put(KEY_PRODUCT_DESCRIPTION, product_description);
        productValues.put(KEY_PRODUCT_PRICE, product_price);
        int count = db.update(TABLE_PRODUCTS, productValues, KEY_PRODUCT_ID+" = ?",new String[]{String.valueOf(product_id)});
        return  count;
    }
}