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

public class InventoryActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Products> listProduct = new ArrayList<>();
    private InventoryAdapter myProductAdapter;
    private RecyclerView rvListProduct;

    private FloatingActionButton fabAddProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        asociateViewXML();
        myProductAdapter = new InventoryAdapter(listProduct); // Initialize the adapter with the empty list
        rvListProduct.setAdapter(myProductAdapter); // Set the adapter
        rvListProduct.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager
        loadTestData(); // Fetch data

        fabAddProduct = findViewById(R.id.fab_add_product);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start AddProductActivity
                Intent intent = new Intent(InventoryActivity.this, AddproductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void asociateViewXML() {
        rvListProduct = findViewById(R.id.rv_products);
    }
    private void loadTestData() {
        db.collection("Productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listProduct.clear(); // Clear the existing products
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Use the toObject() method to convert the Firestore document into a Products object
                                Products myProduct = document.toObject(Products.class);
                                String documentId = document.getId();
                                Products.setDocumentID(documentId);
                                if (myProduct != null) {
                                    listProduct.add(myProduct); // Add the product to the list
                                } else {
                                    Log.e(TAG, "Document " + document.getId() + " could not be converted to a Product object.");
                                }
                            }
                            myProductAdapter.notifyDataSetChanged(); // Notify the adapter of the new data
                        } else {
                            Log.w(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}