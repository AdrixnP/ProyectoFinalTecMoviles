package com.example.proyectomoviles;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private FirebaseFirestore db;
    private ArrayList<Cart> dataSet;
    private OnClickListener onClickListener;

    String billid, cartitemid;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CartAdapter(ArrayList<Cart> dataSet) {
        this.dataSet = dataSet;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView nametext, pricetext, brandtext, yeartext, cantitytext;
        private ImageView imageView, deleteimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.cartproduct_nametext);
            pricetext = itemView.findViewById(R.id.cartproduct_pricetext);
            brandtext = itemView.findViewById(R.id.cartproduct_brandtext);
            yeartext = itemView.findViewById(R.id.cartproduct_yeartext);
            cantitytext = itemView.findViewById(R.id.cart_cantitytext);
            imageView = itemView.findViewById(R.id.cartproduct_img);
            deleteimg = itemView.findViewById(R.id.cartdeletebutton);

        }

        public void loadItem(Cart myCart) {
            nametext.setText(myCart.getProductnamecart() != null ? myCart.getProductnamecart() : "N/A");
            pricetext.setText(myCart.getFixPriceCart() != null ? myCart.getFixPriceCart() : "0");
            brandtext.setText(myCart.getProductbrandcart() != null ? myCart.getProductbrandcart() : "N/A");
            yeartext.setText(myCart.getYearCartProductToStr() != null ? myCart.getYearCartProductToStr() : "N/A");
            cantitytext.setText(myCart.getCantidadProductoCarritoToStr() != null ? myCart.getCantidadProductoCarritoToStr() : "0");
            if (myCart.getProductimgcart() != null && !myCart.getProductimgcart().isEmpty()) {
                Picasso.get()
                        .load(myCart.getProductimgcart())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }

            cartitemid = myCart.getSubdocumentId();
            billid = myCart.getMaindocumentId();

            deleteimg.setOnClickListener(this::deleteonClick);
        }

        private void deleteonClick(View view) {
            deleteProduct(billid, cartitemid);
        }
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_cartproductslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Log.d("CartAdapter", "Binding view holder for position " + position);
        if (dataSet != null && position < dataSet.size()) {
            Cart myCart = dataSet.get(position);
            if (myCart != null) {
                holder.loadItem(myCart);
                Log.d("CartAdapter", "Product bound: " + myCart.toString());
            } else {
                Log.w("CartAdapter", "Product at position " + position + " is null.");
            }
        } else {
            Log.w("CartAdapter", "Data set is null or position out of bounds.");
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnClickListener {
        void onClickDelete(Products myProduct);

        void onClickEdit(Products myProduct);
    }

    private void deleteProduct(String billid,String cartitemid) {
        // Deleting the product from the subcollection in Firestore
        String cartproductid;
        db.collection("Facturas") // Reference the parent document
                .document(billid) // Reference the specific document in the collection
                .collection("Carrito") // Reference the subcollection
                .document(cartitemid) // Reference the specific document in the subcollection
                .delete();
    }
}

