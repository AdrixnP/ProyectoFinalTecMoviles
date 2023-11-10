package com.example.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.HashMap;
import java.util.Map;

public class ShopProductBuydetailsActivity extends AppCompatActivity {

    private Double AmountOperation;
    private Integer cantidadacutualalcomprar = 0;
    private String idfactura;
    private TextView buynametext, buypricetext, buybrandtext, buyyeartext, buydescriptiontext, boughtquantitytext, boughtamounttext;
    private ImageView buyimageView, lessproductbut, moreproductbut;
    private Button addtocartbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_product_buydetails);

        // Initialize the views
        buynametext = findViewById(R.id.productname);
        buypricetext = findViewById(R.id.pricetext);
        buybrandtext = findViewById(R.id.brandtext);
        buyyeartext = findViewById(R.id.yeartext);
        buydescriptiontext = findViewById(R.id.infoproduct);
        buyimageView = findViewById(R.id.imgproduct);
        boughtquantitytext = findViewById(R.id.producttocartcount);

        lessproductbut = findViewById(R.id.leestocart);
        moreproductbut = findViewById(R.id.moretocart);
        addtocartbutton = findViewById(R.id.addtocartbutton);

        // Retrieve the extras from the Intent
        String nombre = getIntent().getStringExtra("nombre");
        String description = getIntent().getStringExtra("description");
        String year = getIntent().getStringExtra("year");
        String marca = getIntent().getStringExtra("marca");
        String imgLink = getIntent().getStringExtra("imglink");
        String precio = getIntent().getStringExtra("precio");
        String cantidad = getIntent().getStringExtra("cantidad");
        long id = getIntent().getLongExtra("id", -1);
        String documentId = getIntent().getStringExtra("idfirebase");


        idfactura = getIntent().getStringExtra("idfactura");

        // Set the retrieved data to the views
        buynametext.setText(nombre);
        buydescriptiontext.setText(description);
        buyyeartext.setText(year);
        buybrandtext.setText(marca);
        buypricetext.setText(precio);
        boughtquantitytext.setText(String.valueOf(cantidadacutualalcomprar));

        // Load the image using Picasso
        if (imgLink != null && !imgLink.isEmpty()) {
            Picasso.get().load(imgLink).into(buyimageView);
        }

        lessproductbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidadacutualalcomprar > 0){
                    cantidadacutualalcomprar = cantidadacutualalcomprar-1;
                    boughtquantitytext.setText(String.valueOf(cantidadacutualalcomprar));
                }

            }
        });

        moreproductbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadacutualalcomprar = cantidadacutualalcomprar + 1;
                boughtquantitytext.setText(String.valueOf(cantidadacutualalcomprar));
            }
        });

        addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountOperation = cantidadacutualalcomprar * Double.parseDouble(precio);
                if (cantidadacutualalcomprar > 0 && idfactura != null) {
                    // Parse precio and year to double and int respectively
                    double parsedPrecio = Double.parseDouble(precio);
                    int parsedYear = Integer.parseInt(year);

                    // Create a Product object with the necessary data
                    Map<String, Object> cartData = new HashMap<>();
                    cartData.put("NombreProducto", nombre);  // replace with the actual product name
                    cartData.put("ImgProducto", imgLink);
                    cartData.put("PrecioProducto", parsedPrecio);  // Save as double
                    cartData.put("CantidadProducto", cantidadacutualalcomprar);
                    cartData.put("MarcaProducto", marca);
                    cartData.put("YearProducto", parsedYear);  // Save as int
                    cartData.put("PrecioTotalCarrito", AmountOperation);

                    // Get a reference to the Firestore database
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Reference to the specific document in the Bills collection
                    DocumentReference billDocumentRef = db.collection("Facturas").document(idfactura);

                    // Reference to the "Producto Carrito" subcollection within the specific bill document
                    CollectionReference productsCollectionRef = billDocumentRef.collection("Productos Carrito");

                    // Add the productData to the "Producto Carrito" subcollection
                    productsCollectionRef.add(cartData)
                            .addOnSuccessListener(documentReference -> {
                                // Handle success (product added to subcollection)
                                Toast.makeText(ShopProductBuydetailsActivity.this, "Product added to the bill" + idfactura, Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                                Toast.makeText(ShopProductBuydetailsActivity.this, "Error adding product to the bill", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    // Handle the case where cantidadacutualalcomprar is not greater than 0 or idfactura is null
                    Toast.makeText(ShopProductBuydetailsActivity.this, "Invalid quantity or bill ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}