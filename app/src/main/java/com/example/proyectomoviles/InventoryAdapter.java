package com.example.proyectomoviles;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private ArrayList<Products> dataSet;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public InventoryAdapter(ArrayList<Products> dataSet) {
        this.dataSet = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nametext, pricetext, brandtext, yeartext, cantitytext;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.product_nametext);
            pricetext = itemView.findViewById(R.id.product_pricetext);
            brandtext = itemView.findViewById(R.id.product_brandtext);
            yeartext = itemView.findViewById(R.id.product_yeartext);
            cantitytext = itemView.findViewById(R.id.product_cantitytext);
            imageView = itemView.findViewById(R.id.product_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Products product = dataSet.get(position);
                        Intent intent = new Intent(v.getContext(), ProductdetailsActivity.class);
                        intent.putExtra("nombre", product.getNombre());
                        intent.putExtra("description", product.getDescription()); // corrected the key "desciption" to "description"
                        intent.putExtra("year", product.getYearToStr());
                        intent.putExtra("yearnumero", product.getYear());
                        intent.putExtra("marca", product.getMarca());
                        intent.putExtra("imglink", product.getImgLink());
                        intent.putExtra("precio", product.getPriceToStr());
                        intent.putExtra("precionumero", product.getPrecio());
                        intent.putExtra("cantidad", product.getCantidadToStr()); // adding cantidad
                        intent.putExtra("cantidadnumero", product.getCantidad());
                        intent.putExtra("id", product.getId());
                        intent.putExtra("idfirebase", product.getDocumentID());// adding id

                        v.getContext().startActivity(intent);
                    }
                }
            });

        }

        public void loadItem(Products myProduct) {
            nametext.setText(myProduct.getNombre() != null ? myProduct.getNombre() : "N/A");
            pricetext.setText(myProduct.getPriceToStr() != null ? myProduct.getPriceToStr() : "0");
            brandtext.setText(myProduct.getMarca() != null ? myProduct.getMarca() : "N/A");
            yeartext.setText(myProduct.getYearToStr() != null ? myProduct.getYearToStr() : "N/A");
            cantitytext.setText(myProduct.getCantidadToStr() != null ? myProduct.getCantidadToStr() : "0");
            if (myProduct.getImgLink() != null && !myProduct.getImgLink().isEmpty()) {
                Picasso.get()
                        .load(myProduct.getImgLink())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }
        }

    }


    @NonNull
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.ViewHolder holder, int position) {
        Log.d("InventoryAdapter", "Binding view holder for position " + position);
        if (dataSet != null && position < dataSet.size()) {
            Products myProduct = dataSet.get(position);
            if (myProduct != null) {
                holder.loadItem(myProduct);
                Log.d("InventoryAdapter", "Product bound: " + myProduct.toString());
            } else {
                Log.w("InventoryAdapter", "Product at position " + position + " is null.");
            }
        } else {
            Log.w("InventoryAdapter", "Data set is null or position out of bounds.");
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
