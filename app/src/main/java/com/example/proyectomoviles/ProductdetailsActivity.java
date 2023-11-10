package com.example.proyectomoviles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductdetailsActivity extends AppCompatActivity {

    // Member variables for the text views and image view
    private TextView nametext, pricetext, brandtext, yeartext, descriptiontext, quantitytext, codetext;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        // Initialize the views
        nametext = findViewById(R.id.productname);
        pricetext = findViewById(R.id.pricetext);
        brandtext = findViewById(R.id.brandtext);
        yeartext = findViewById(R.id.yeartext);
        descriptiontext = findViewById(R.id.infoproduct);
        imageView = findViewById(R.id.imgproduct);
        quantitytext = findViewById(R.id.productstocktext);
        codetext = findViewById(R.id.productidtext);

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

        int yearint = getIntent().getIntExtra("yearnumero", -1);
        int cantidadint = getIntent().getIntExtra("cantidadnumero", -1);
        int preciodouble = getIntent().getIntExtra("precionumero", -1);

        // Set the retrieved data to the views
        nametext.setText(nombre);
        descriptiontext.setText(description);
        yeartext.setText(year);
        brandtext.setText(marca);
        pricetext.setText(precio);
        quantitytext.setText(cantidad);
        codetext.setText(String.valueOf(id));

        // Load the image using Picasso
        if (imgLink != null && !imgLink.isEmpty()) {
            Picasso.get().load(imgLink).into(imageView);
        }

        // Set up the edit button to go to the EditProductActivity
        ImageView editButton = findViewById(R.id.editbutton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ProductdetailsActivity.this, EditproductActivity.class);
                // Pass the product details to the edit activity
                editIntent.putExtra("nombre", nombre);
                editIntent.putExtra("description", description);
                editIntent.putExtra("year", yearint);
                editIntent.putExtra("marca", marca);
                editIntent.putExtra("imglink", imgLink);
                editIntent.putExtra("precio", preciodouble);
                editIntent.putExtra("cantidad", cantidadint);
                editIntent.putExtra("id", id);
                editIntent.putExtra("firebaseId", documentId);
                startActivity(editIntent);
            }
        });
    }
}