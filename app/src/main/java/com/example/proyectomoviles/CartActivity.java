package com.example.proyectomoviles;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView Totalamountbilltext, totalproductsonbill;
    private EditText billclientNameInput, billphoneclienteInput, BillsellernameInput, billdateinput;
    private FloatingActionButton fabAddProductCart, fabDeleteBill;
    private ImageView saveEditButton, backEditButton, deletebutton, productimgcart;

    private ArrayList<Cart> listCart = new ArrayList<>();
    private CartAdapter myCartAdapter;
    private RecyclerView rvListCart;
    private Double Cadaproductovalortotal, preciototaldelafactura;
    private Integer totaldeproductosenfactura;

    String billId;
    Double NumeroPreciototal;
    Integer Numerototaldeproductos;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        billId = getIntent().getStringExtra("idfacturafirebase");
        preciototaldelafactura= (double) 0;
        totaldeproductosenfactura = 0;

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        billclientNameInput = findViewById(R.id.billclientnameinput);
        billphoneclienteInput = findViewById(R.id.billclientphoneinput);
        billdateinput = findViewById(R.id.billdateinput);
        BillsellernameInput = findViewById(R.id.billsellerinput);
        Totalamountbilltext = findViewById(R.id.billtotalamount);
        totalproductsonbill = findViewById(R.id.billtotalproducts);

        asociateViewXML();
        myCartAdapter = new CartAdapter(listCart); // Initialize the adapter with the empty list
        rvListCart.setAdapter(myCartAdapter); // Set the adapter
        rvListCart.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager
        loadSubcollectionData();

        String clientefactura = getIntent().getStringExtra("nombre");
        String telefonofactura = getIntent().getStringExtra("telefono");
        String vendedorfactura = getIntent().getStringExtra("vendedor");
        String fechalafactura = getIntent().getStringExtra("fecha");

        billclientNameInput.setText(clientefactura);
        billphoneclienteInput.setText(telefonofactura);
        billdateinput.setText(fechalafactura);
        BillsellernameInput.setText(vendedorfactura);
        Totalamountbilltext.setText("Total: $"+preciototaldelafactura);
        totalproductsonbill.setText("Productos: "+totaldeproductosenfactura);

        fabAddProductCart = findViewById(R.id.fab_addproductbill);
        fabAddProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start AddProductActivity
                Intent intent = new Intent(CartActivity.this, ShopProductActivity.class);
                intent.putExtra("idfacturafirebase", billId);
                startActivity(intent);
            }
        });

        fabDeleteBill = findViewById(R.id.fab_deletebill);
        fabDeleteBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start AddProductActivity
                db.collection("Productos").document(billId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(CartActivity.this, "Factura borrada "+ billId, Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(CartActivity.this, "Error al borrar factura", Toast.LENGTH_SHORT).show());
            }
        });

        saveEditButton = findViewById(R.id.saveimg);
        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = billclientNameInput.getText().toString().trim();
                String phonestr = billphoneclienteInput.getText().toString().trim();
                String sellername = BillsellernameInput.getText().toString().trim();
                String datetext = billdateinput.getText().toString().trim();

                int phone = TextUtils.isEmpty(phonestr) ? 0 : Integer.parseInt(phonestr);

                // Start AddProductActivity
                Map<String, Object> billMap = new HashMap<>();
                billMap.put("Cliente", name);
                billMap.put("Telefono", phone);
                billMap.put("Vendedor", sellername);
                billMap.put("Fecha", datetext);
                billMap.put("PrecioTotal", preciototaldelafactura);
                billMap.put("CantidadProductos", totaldeproductosenfactura);

                // Saving data to Firestore
                db.collection("Facturas").document(billId)
                        .set(billMap)
                        .addOnSuccessListener(aVoid -> Toast.makeText(CartActivity.this, "Factura actualizada "+ billId, Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(CartActivity.this, "Error al actualizar factura", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void asociateViewXML() {
        rvListCart = findViewById(R.id.rv_cartproducts);
    }
    private void loadSubcollectionData() {
        db.collection("Facturas") // Reference the parent document
                .document(billId) // Reference the specific document
                .collection("Productos Carrito") // Reference the subcollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listCart.clear(); // Clear the existing bills
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Process each document in the subcollection
                                Cart myCart = document.toObject(Cart.class);
                                // Assuming the date is stored under a field named "creationDate" in Firestore
                                String subdocumentId = document.getId();

                                Log.d("manuel",subdocumentId);

                                Cart.setSavesubid(subdocumentId);

                                Cart.setSavemainid(billId);

                                Cadaproductovalortotal = Cart.getTotalamountproductoncart();
                                preciototaldelafactura += Cadaproductovalortotal;

                                totaldeproductosenfactura += 1;
                                if (myCart != null) {
                                    listCart.add(myCart); // Add the product to the list
                                } else {
                                    Log.e(TAG, "Document " + document.getId() + " could not be converted to a Product object.");
                                }
                            }
                            myCartAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}