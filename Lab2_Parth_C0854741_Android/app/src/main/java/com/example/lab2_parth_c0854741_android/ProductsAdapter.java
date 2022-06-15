package com.example.lab2_parth_c0854741_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_parth_c0854741_android.databinding.ActivityMainBinding;
import com.example.lab2_parth_c0854741_android.databinding.MylistBinding;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    Context context;
    ProductClickListener mListener;
    ArrayList<ProductModel> productList;

    public interface ProductClickListener{
        void onDeleteClickListener(int position);
        void onUpdateClickListener(int adapterPosition);
    }

    ProductsAdapter(Context context, ProductClickListener mListener){
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(MylistBinding.inflate((LayoutInflater.from(context)), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.setDataToView(productList.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void doRefresh(ArrayList<ProductModel> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MylistBinding itemView;
        public MyViewHolder(@NonNull MylistBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
            init();
        }

        private void init() {
            itemView.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDeleteClickListener(getAdapterPosition());
                }
            });

            itemView.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onUpdateClickListener(getAdapterPosition());
                }
            });
        }

        public void setDataToView(ProductModel productModel) {
            itemView.title.setText(productModel.getProductName());
            itemView.subtitle.setText(productModel.getProductDescription());
            itemView.price.setText("$" + productModel.getProductPrice());
        }
    }
}
