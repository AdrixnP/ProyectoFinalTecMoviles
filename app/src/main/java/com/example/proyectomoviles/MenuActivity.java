package com.example.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            if(TextUtils.isEmpty(name)) {
                name = "Usuario"; // Default display name
            }
            TextView usernameTextView = findViewById(R.id.usernametextmenu);
            usernameTextView.setText(name);
        }

        Button logoutButton = findViewById(R.id.menulogoutbutton);
        logoutButton.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // To close the current activity
        });

        // Set up inventory and bills buttons
        Button inventoryButton = findViewById(R.id.menuinventorybutton);
        Button billsButton = findViewById(R.id.menubillbutton);

        inventoryButton.setOnClickListener(view -> {
            startActivity(new Intent(MenuActivity.this, InventoryActivity.class));
        });

        billsButton.setOnClickListener(view -> {
            startActivity(new Intent(MenuActivity.this, BillsActivity.class));
        });
    }
}

