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
import java.util.Date;

public class BillsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Bills> listBill = new ArrayList<>();
    private BillsAdapter myBillAdapter;
    private RecyclerView rvListBill;

    private FloatingActionButton fabAddBill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        asociateViewXML();
        myBillAdapter = new BillsAdapter(listBill); // Initialize the adapter with the empty list
        rvListBill.setAdapter(myBillAdapter); // Set the adapter
        rvListBill.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager
        loadTestData(); // Fetch data

        fabAddBill = findViewById(R.id.fab_add_bill);
        fabAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start AddProductActivity
                Intent intent = new Intent(BillsActivity.this, AddbillActivity.class);
                startActivity(intent);
            }
        });
    }

    private void asociateViewXML() {
        rvListBill = findViewById(R.id.rv_bills);
    }
    private void loadTestData() {
        db.collection("Facturas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listBill.clear(); // Clear the existing bills
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bills myBill = document.toObject(Bills.class);
                                // Assuming the date is stored under a field named "creationDate" in Firestore
                                String documentId = document.getId();
                                Bills.setDocumentID(documentId);
                                if (myBill != null) {
                                    listBill.add(myBill); // Add the product to the list
                                } else {
                                    Log.e(TAG, "Document " + document.getId() + " could not be converted to a Product object.");
                                }
                            }
                            myBillAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}