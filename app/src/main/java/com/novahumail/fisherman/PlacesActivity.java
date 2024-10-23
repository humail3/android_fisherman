package com.novahumail.fisherman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PlacesActivity extends AppCompatActivity {
    private EditText etName, etCountry, etRegion, etLake;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        etName = findViewById(R.id.ET_name_places);
        etCountry = findViewById(R.id.ET_country_places);
        etRegion = findViewById(R.id.ET_region_places);
        etLake = findViewById(R.id.ET_lake_places);

        btnAdd = findViewById(R.id.BTN_add_places);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from EditText fields
                String name = etName.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                String region = etRegion.getText().toString().trim();
                String lake = etLake.getText().toString().trim();

                // Validate if any field is empty
                if (name.isEmpty() || country.isEmpty() || region.isEmpty() || lake.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform your action to add the data to Firebase or any other action
                    addDataToFirestore(name, country, region, lake);
                }
            }
        });


    }


    // Method to add data to Firestore under the user's ID
    private void addDataToFirestore(String name, String country, String region, String lake) {
        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get user ID
            String userId = currentUser.getUid();

            // Get Firestore instance
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Create a HashMap to store the data
            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("country", country);
            data.put("region", region);
            data.put("lake", lake);

            // Add the data to Firestore under the "shots" collection
            db.collection("users")
                    .document(userId)
                    .collection("places").document(name)
                    .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Document added successfully
                            Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MyPlacesActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error occurred while adding the document
                            Toast.makeText(getApplicationContext(), "Unable to Add", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


}