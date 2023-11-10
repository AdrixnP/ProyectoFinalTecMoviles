package com.example.proyectomoviles;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ViewHolder> {

    private ArrayList<Products> dataSet;
    private ShopProductAdapter.OnClickListener onClickListener;
    private Intent activityIntent;
    String bill;

    public ShopProductAdapter(ArrayList<Products> dataSet, Intent activityIntent) {
        this.dataSet = dataSet;
        this.activityIntent = activityIntent != null ? activityIntent : new Intent(); // Use the provided Intent or a default one
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nametextshop, pricetextshop, brandtextshop, yeartextshop;
        private ImageView imageViewshop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nametextshop = itemView.findViewById(R.id.shopproduct_nametext);
            pricetextshop = itemView.findViewById(R.id.shopproduct_pricetext);
            brandtextshop = itemView.findViewById(R.id.shopproduct_brandtext);
            yeartextshop = itemView.findViewById(R.id.shopproduct_yeartext);
            imageViewshop = itemView.findViewById(R.id.shopproduct_img);
            // Use the Intent from the activity to get the idfactura
            String bill = activityIntent.getStringExtra("idfacturafirebase");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Products product = dataSet.get(position);

                        Intent intent = new Intent(v.getContext(), ShopProductBuydetailsActivity.class);
                        intent.putExtra("nombre", product.getNombre());
                        intent.putExtra("description", product.getDescription());
                        intent.putExtra("year", product.getYearToStr());
                        intent.putExtra("marca", product.getMarca());
                        intent.putExtra("imglink", product.getImgLink());
                        intent.putExtra("precio", product.getPriceToStr());
                        intent.putExtra("cantidad", product.getCantidadToStr());
                        intent.putExtra("id", product.getId());
                        intent.putExtra("idfirebase", product.getDocumentID());

                        intent.putExtra("idfactura", bill);

                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void loadItem(Products myProduct) {
            nametextshop.setText(myProduct.getNombre() != null ? myProduct.getNombre() : "N/A");
            pricetextshop.setText(myProduct.getPriceToStr() != null ? myProduct.getPriceToStr() : "0");
            brandtextshop.setText(myProduct.getMarca() != null ? myProduct.getMarca() : "N/A");
            yeartextshop.setText(myProduct.getYearToStr() != null ? myProduct.getYearToStr() : "N/A");
            if (myProduct.getImgLink() != null && !myProduct.getImgLink().isEmpty()) {
                Picasso.get()
                        .load(myProduct.getImgLink())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageViewshop);
            } else {
                imageViewshop.setImageResource(R.drawable.ic_launcher_background);
            }
        }

    }


    @NonNull
    @Override
    public ShopProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_productshoplist, parent, false);
        return new ShopProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopProductAdapter.ViewHolder holder, int position) {
        Log.d("ShopProductAdapter", "Binding view holder for position " + position);
        if (dataSet != null && position < dataSet.size()) {
            Products myProduct = dataSet.get(position);
            if (myProduct != null) {
                holder.loadItem(myProduct);
                Log.d("ShopProductAdapter", "Product bound: " + myProduct.toString());
            } else {
                Log.w("ShopProductAdapter", "Product at position " + position + " is null.");
            }
        } else {
            Log.w("ShopProductAdapter", "Data set is null or position out of bounds.");
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
}