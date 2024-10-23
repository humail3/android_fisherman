package com.novahumail.fisherman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowPlaces extends AppCompatActivity {
    private TextView nameTV, countryTV, regionTV, lakeTV;
    private String name = "", country = "", region = "", lake = "";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);


        nameTV = findViewById(R.id.TV_name_showPlaces);
        countryTV = findViewById(R.id.TV_country_showPlaces);
        regionTV = findViewById(R.id.TV_region_showPlaces);
        lakeTV = findViewById(R.id.TV_lake_showPlaces);


        name = getIntent().getStringExtra("placeName");

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
            db.collection("users").document(userId).collection("places").document(name)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Retrieve the attributes from the document
                                country = document.getString("country");
                                region = document.getString("region");
                                lake = document.getString("lake");

                                // Update TextViews with the fetched data
                                nameTV.setText(name);
                                countryTV.setText(country);
                                regionTV.setText(region);
                                lakeTV.setText(lake);
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
