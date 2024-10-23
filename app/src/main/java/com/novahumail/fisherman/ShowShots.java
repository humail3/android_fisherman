package com.novahumail.fisherman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowShots extends AppCompatActivity {

    private TextView nameTV, pressureTV, temperatureTV, montageTV, baitTV, windTV, placeTV;
    private String name = "", pressure = "", temperature = "", montage = "", bait = "", wind = "", place = "";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shots);


        nameTV = findViewById(R.id.TV_name_showShots);
        pressureTV = findViewById(R.id.TV_pressure_showShots);
        temperatureTV = findViewById(R.id.TV_temperature_showShots);
        montageTV = findViewById(R.id.TV_montage_showShots);
        baitTV = findViewById(R.id.TV_bait_showShots);
        windTV = findViewById(R.id.TV_wind_showShots);
        placeTV = findViewById(R.id.TV_place_showShots);


        name = getIntent().getStringExtra("shotName");

        fetchShotDataFromFirestore(name);


    }


    // Method to fetch shot data based on its name
    private void fetchShotDataFromFirestore(String name) {
        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        if (currentUser != null) {
            // Get user ID
            String userId = currentUser.getUid();

            // Query Firestore to fetch the document data for the given shot name
            db.collection("users").document(userId).collection("shots").document(name)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Retrieve the attributes from the document
                                pressure = document.getString("pressure");
                                temperature = document.getString("temperature");
                                montage = document.getString("montage");
                                bait = document.getString("bait");
                                wind = document.getString("wind");
                                place = document.getString("place");

                                // Update TextViews with the fetched data
                                nameTV.setText(name);
                                pressureTV.setText(pressure);
                                temperatureTV.setText(temperature);
                                montageTV.setText(montage);
                                baitTV.setText(bait);
                                windTV.setText(wind);
                                placeTV.setText(place);
                            } else {
                                Log.d("Firestore", "No such document");
                                Toast.makeText(getApplicationContext(), "Shot data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("Firestore", "Error getting document: ", task.getException());
                            Toast.makeText(getApplicationContext(), "Error fetching shot data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}
