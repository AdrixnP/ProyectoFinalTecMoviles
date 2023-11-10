package com.example.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditproductActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText productNameInput, productDescriptionInput, productImgInput,
            productBrandInput, productYearInput, productPriceInput, productQuantityInput, productIdInput;
    private Button deleteProductButton;
    private ImageView saveEditButton, backEditButton;

    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproduct);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        productNameInput = findViewById(R.id.productnameinput);
        productDescriptionInput = findViewById(R.id.productdescriptioninput);
        productImgInput = findViewById(R.id.productimginput);
        productBrandInput = findViewById(R.id.productbrandinput);
        productYearInput = findViewById(R.id.productyearinput);
        productPriceInput = findViewById(R.id.productpriceinput);
        productQuantityInput = findViewById(R.id.productcantityinput);
        productIdInput = findViewById(R.id.productidinput);

        deleteProductButton = findViewById(R.id.buttondeleteproduct);
        saveEditButton = findViewById(R.id.saveeditbutton);
        backEditButton = findViewById(R.id.backeditbutton);

        // Retrieve the product ID from the Intent
        Intent intent = getIntent();
        productId = getIntent().getStringExtra("firebaseId");

        if (intent != null) {
            long id = intent.getLongExtra("id", -1);
            if (id != -1) {
                String nombre = intent.getStringExtra("nombre");
                String description = intent.getStringExtra("description");
                String year = intent.getStringExtra("year");
                String marca = intent.getStringExtra("marca");
                String imgLink = intent.getStringExtra("imglink");
                String precio = intent.getStringExtra("precio");
                String cantidad = intent.getStringExtra("cantidad");

                productNameInput.setText(nombre);
                productDescriptionInput.setText(description);
                productImgInput.setText(imgLink);
                productBrandInput.setText(marca);
                productYearInput.setText(year);
                productPriceInput.setText(precio);
                productQuantityInput.setText(cantidad);
                productIdInput.setText(String.valueOf(id));


            } else {
                Toast.makeText(this, "Error: Product ID is missing.", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        saveEditButton.setOnClickListener(view -> saveProduct());
        deleteProductButton.setOnClickListener(view -> deleteProduct());
        backEditButton.setOnClickListener(view -> finish());
    }

    private void loadProductDetails(String productId) {
        // Fetch the product details from Firestore and display them in the EditText fields
        db.collection("Productos").document(productId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        productNameInput.setText(documentSnapshot.getString("Nombre"));
                        productDescriptionInput.setText(documentSnapshot.getString("Description"));
                        productImgInput.setText(documentSnapshot.getString("ImgLink"));
                        productBrandInput.setText(documentSnapshot.getString("Marca"));

                        // Assuming "Year", "Precio", "Cantidad", and "ID" are stored as Longs in Firestore
                        Long year = documentSnapshot.getLong("Year");
                        Double price = documentSnapshot.getDouble("Precio");
                        Long quantity = documentSnapshot.getLong("Cantidad");
                        Long id = documentSnapshot.getLong("ID");

                        // Set the values in the EditText fields, converting numbers to strings
                        productYearInput.setText(year != null ? String.valueOf(year) : "");
                        productPriceInput.setText(price != null ? String.format("%.2f", price) : "");
                        productQuantityInput.setText(quantity != null ? String.valueOf(quantity) : "");
                        productIdInput.setText(id != null ? String.valueOf(id) : "");
                    } else {
                        Toast.makeText(EditproductActivity.this, "Product does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(EditproductActivity.this, "Error loading product", Toast.LENGTH_SHORT).show());
    }

    private void saveProduct() {
        // Collecting input data
        Map<String, Object> productData = new HashMap<>();
        productData.put("Nombre", productNameInput.getText().toString());
        productData.put("Description", productDescriptionInput.getText().toString());
        productData.put("ImgLink", productImgInput.getText().toString());
        productData.put("Marca", productBrandInput.getText().toString());
        productData.put("Year", Long.parseLong(productYearInput.getText().toString()));
        productData.put("Precio", Double.parseDouble(productPriceInput.getText().toString()));
        productData.put("Cantidad", Long.parseLong(productQuantityInput.getText().toString()));
        productData.put("ID", Long.parseLong(productIdInput.getText().toString()));

        // Saving data to Firestore
        db.collection("Productos").document(productId)
                .set(productData)
                .addOnSuccessListener(aVoid -> Toast.makeText(EditproductActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(EditproductActivity.this, "Error al actualizar producto", Toast.LENGTH_SHORT).show());
    }

    private void deleteProduct() {
        // Deleting the product from Firestore
        db.collection("Productos").document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditproductActivity.this, "Producto borrado", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditproductActivity.this, "Error al borrar producto", Toast.LENGTH_SHORT).show());
    }
}
