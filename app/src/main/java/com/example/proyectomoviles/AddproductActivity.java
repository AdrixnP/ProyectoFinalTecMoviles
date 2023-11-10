package com.example.proyectomoviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddproductActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    // UI references.
    private EditText productNameInput, productDescriptionInput, productImgInput,
            productBrandInput, productYearInput, productPriceInput, productQuantityInput,
            productIdInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Setup UI references
        productNameInput = findViewById(R.id.productnameinput);
        productDescriptionInput = findViewById(R.id.productdescriptioninput);
        productImgInput = findViewById(R.id.productimginput);
        productBrandInput = findViewById(R.id.productbrandinput);
        productYearInput = findViewById(R.id.productyearinput);
        productPriceInput = findViewById(R.id.productpriceinput);
        productQuantityInput = findViewById(R.id.productcantityinput);
        productIdInput = findViewById(R.id.productidinput);

        ImageView saveButton = findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });
    }

    private void saveProduct() {
        String name = productNameInput.getText().toString().trim();
        String description = productDescriptionInput.getText().toString().trim();
        String imageUrl = productImgInput.getText().toString().trim();
        String brand = productBrandInput.getText().toString().trim();
        String yearStr = productYearInput.getText().toString().trim();
        String priceStr = productPriceInput.getText().toString().trim();
        String quantityStr = productQuantityInput.getText().toString().trim();
        String idStr = productIdInput.getText().toString().trim();

        int year = TextUtils.isEmpty(yearStr) ? -1 : Integer.parseInt(yearStr);
        double price = TextUtils.isEmpty(priceStr) ? 0.0 : Double.parseDouble(priceStr);
        int quantity = TextUtils.isEmpty(quantityStr) ? 0 : Integer.parseInt(quantityStr);
        long id = TextUtils.isEmpty(idStr) ? -1 : Long.parseLong(idStr);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || year == -1 || price == 0.0 || quantity == 0 || id == -1) {
            Toast.makeText(this, "Please fill in all the fields correctly.", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("Nombre", name);
        productMap.put("Description", description);
        productMap.put("ImgLink", imageUrl);
        productMap.put("Marca", brand);
        productMap.put("Year", year);
        productMap.put("Precio", price);
        productMap.put("Cantidad", quantity);
        productMap.put("ID", id);

        db.collection("Productos").document() // document() without argument will generate a new ID
                .set(productMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddproductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        // Optionally clear the input fields or close the activity
                        clearInputs();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddproductActivity.this, "Error adding product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearInputs() {
        productNameInput.setText("");
        productDescriptionInput.setText("");
        productImgInput.setText("");
        productBrandInput.setText("");
        productYearInput.setText("");
        productPriceInput.setText("");
        productQuantityInput.setText("");
        productIdInput.setText("");
    }
}
