package com.example.proyectomoviles;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShopProductActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Products> listProductShop = new ArrayList<>();
    private ShopProductAdapter myShopProductAdapter;
    private RecyclerView rvListProductShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_product);
        asociateViewXML();
        myShopProductAdapter = new ShopProductAdapter(listProductShop, getIntent()); // Initialize the adapter with the empty list
        rvListProductShop.setAdapter(myShopProductAdapter); // Set the adapter
        rvListProductShop.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager
        loadTestData(); // Fetch data
    }

    private void asociateViewXML() {
        rvListProductShop = findViewById(R.id.rv_shopproducts);
    }
    private void loadTestData() {
        db.collection("Productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listProductShop.clear(); // Clear the existing products
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Use the toObject() method to convert the Firestore document into a Products object
                                Products myProduct = document.toObject(Products.class);
                                String documentId = document.getId();
                                Products.setDocumentID(documentId);
                                if (myProduct != null) {
                                    listProductShop.add(myProduct); // Add the product to the list
                                } else {
                                    Log.e(TAG, "Document " + document.getId() + " could not be converted to a Product object.");
                                }
                            }
                            myShopProductAdapter.notifyDataSetChanged(); // Notify the adapter of the new data
                        } else {
                            Log.w(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}