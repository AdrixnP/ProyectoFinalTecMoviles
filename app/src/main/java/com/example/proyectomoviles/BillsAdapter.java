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

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {

    private ArrayList<Bills> dataSet;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public BillsAdapter(ArrayList<Bills> dataSet) {
        this.dataSet = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView billname, billphone, billproductamount, billtotalamount, billsellername, billdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billname = itemView.findViewById(R.id.billnametext);
            billphone = itemView.findViewById(R.id.billphonetext);
            billproductamount = itemView.findViewById(R.id.billproductamounttext);
            billtotalamount = itemView.findViewById(R.id.billamountnametext);
            billsellername = itemView.findViewById(R.id.billsellernametext);
            billdate = itemView.findViewById(R.id.billdatetext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Bills bill = dataSet.get(position);
                        Intent intent = new Intent(v.getContext(), CartActivity.class);
                        intent.putExtra("nombre", bill.getCustomerName());
                        intent.putExtra("telefono", bill.getFixPhone());
                        intent.putExtra("totalproductos", bill.getProductCount());
                        intent.putExtra("totalcifra", bill.getTotalAmount());
                        intent.putExtra("vendedor", bill.getUserName());
                        intent.putExtra("fecha", bill.getCreationDate());
                        intent.putExtra("idfacturafirebase", bill.getDocumentID());
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }

        public void loadItem(Bills myBill) {
            billname.setText(myBill.getCustomerName() != null ? myBill.getCustomerName() : "N/A");
            billphone.setText(myBill.getFixPhone() != null ? myBill.getFixPhone() : "N/A");
            billproductamount.setText(myBill.getFixCount() != null ? myBill.getFixCount() : "0");
            billtotalamount.setText(myBill.getFixAmount() != null ? myBill.getFixAmount() : "0");
            billsellername.setText(myBill.getUserName() != null ? myBill.getUserName() : "N/A");
            billdate.setText(myBill.getCreationDate() != null ? myBill.getCreationDate() : "N/A");
        }

    }

    @NonNull
    @Override
    public BillsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_billlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillsAdapter.ViewHolder holder, int position) {
        Log.d("BillsAdapter", "Binding view holder for position " + position);
        if (dataSet != null && position < dataSet.size()) {
            Bills myBill = dataSet.get(position);
            if (myBill != null) {
                holder.loadItem(myBill);
                Log.d("BillsAdapter", "Bill bound: " + myBill.toString());
            } else {
                Log.w("BillsAdapter", "Bill at position " + position + " is null.");
            }
        } else {
            Log.w("BillsAdapter", "Data set is null or position out of bounds.");
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnClickListener {
        void onClickDelete(Bills myBill);

        void onClickEdit(Bills myBill);
    }
}
