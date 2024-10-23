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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TakesActivity extends AppCompatActivity {

    private EditText etName, etPressure, etTemperature, etMontage, etBait, etWind, etPlace;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takes);

        etName = findViewById(R.id.ET_name_takes);
        etPressure = findViewById(R.id.ET_pressure_takes);
        etTemperature = findViewById(R.id.ET_temperature_takes);
        etMontage = findViewById(R.id.ET_montage_takes);
        etBait = findViewById(R.id.ET_bait_takes);
        etWind = findViewById(R.id.ET_wind_takes);
        etPlace = findViewById(R.id.ET_place_takes);

        btnAdd = findViewById(R.id.BTN_add_takes);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from EditText fields
                String name = etName.getText().toString().trim();
                String pressure = etPressure.getText().toString().trim();
                String temperature = etTemperature.getText().toString().trim();
                String montage = etMontage.getText().toString().trim();
                String bait = etBait.getText().toString().trim();
                String wind = etWind.getText().toString().trim();
                String place = etPlace.getText().toString().trim();

                // Validate if any field is empty
                if (name.isEmpty() || pressure.isEmpty() || temperature.isEmpty() || montage.isEmpty() || bait.isEmpty() || wind.isEmpty() || place.isEmpty()) {
                    Toast.makeText(TakesActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform your action to add the data to Firebase or any other action
                    addDataToFirestore(name, pressure, temperature, montage, bait, wind, place);
                }
            }
        });


    }


    // Method to add data to Firestore under the user's ID
    private void addDataToFirestore(String name, String pressure, String temperature, String montage, String bait, String wind, String place) {
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
            data.put("pressure", pressure);
            data.put("temperature", temperature);
            data.put("montage", montage);
            data.put("bait", bait);
            data.put("wind", wind);
            data.put("place", place);

            // Add the data to Firestore under the "shots" collection
            db.collection("users")
                    .document(userId)
                    .collection("shots").document(name)
                    .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Document added successfully
                            Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MyShotsActivity.class));
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