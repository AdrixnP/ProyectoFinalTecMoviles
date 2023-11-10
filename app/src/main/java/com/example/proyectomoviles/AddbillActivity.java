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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.HashMap;
import java.util.Map;

public class AddbillActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    // UI references.
    private EditText billNameInput, billphoneInput, billsellerInput, billdateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Setup UI references
        billNameInput = findViewById(R.id.billnameinput);
        billphoneInput = findViewById(R.id.bilpohonenumberinput);
        billsellerInput = findViewById(R.id.billsellernameinput);
        billdateInput = findViewById(R.id.billdatetextinput);

        ImageView saveButton = findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });
    }

    private void saveProduct() {
        String name = billNameInput.getText().toString().trim();
        String phonestr = billphoneInput.getText().toString().trim();
        String sellername = billsellerInput.getText().toString().trim();
        String datetext = billdateInput.getText().toString().trim();

        int phone = TextUtils.isEmpty(phonestr) ? 0 : Integer.parseInt(phonestr);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phonestr) || TextUtils.isEmpty(sellername)) {
            Toast.makeText(this, "Please fill in all the fields correctly.", Toast.LENGTH_LONG).show();
            return;
        }

        // Create a map for the main bill document
        Map<String, Object> billMap = new HashMap<>();
        billMap.put("Cliente", name);
        billMap.put("Telefono", phone);
        billMap.put("Vendedor", sellername);
        billMap.put("Fecha", datetext);
        billMap.put("PrecioTotal", 0);
        billMap.put("NumProductos", 0);

        // Get a reference to the main bill document and store its ID
        DocumentReference mainDocumentRef = db.collection("Facturas").document();
        String mainDocumentId = mainDocumentRef.getId();

        // Set the data for the main bill document
        mainDocumentRef.set(billMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Now, use the ID generated for the main document to create a reference
                        DocumentReference mainDocumentRef = db.collection("Facturas").document(mainDocumentId);

                        // Create the "Productos Carrito" subcollection
                        CollectionReference productosCarritoRef = mainDocumentRef.collection("Productos Carrito");

                        // Create a simple subdocument
                        Map<String, Object> placeholder = new HashMap<>();
                        placeholder.put("IDFacturaReferencia", mainDocumentId);

                        // Add the subdocument to the subcollection
                        productosCarritoRef.add(placeholder)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(AddbillActivity.this, "Bill and subdocument added successfully", Toast.LENGTH_SHORT).show();
                                        // Vaciar la subcolección después de agregar el subdocumento
                                        clearSubcollection(productosCarritoRef);

                                        // Opcionalmente, limpiar los campos de entrada o cerrar la actividad
                                        clearInputs();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddbillActivity.this, "Error adding subdocument: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddbillActivity.this, "Error adding bill: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearInputs() {
        billNameInput.setText("");
        billphoneInput.setText("");
        billsellerInput.setText("");
    }

    // Función para vaciar la subcolección
    private void clearSubcollection(CollectionReference collectionReference) {
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Iterar sobre los documentos y eliminarlos
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            documentSnapshot.getReference().delete();
                        }
                        Toast.makeText(AddbillActivity.this, "Subcollection cleared successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddbillActivity.this, "Error clearing subcollection: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
