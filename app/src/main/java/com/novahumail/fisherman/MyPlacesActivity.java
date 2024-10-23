package com.novahumail.fisherman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyPlacesActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;

    private RecyclerView recyclerView;
    private MyPlacesAdapter adapter;
    private List<MyPlaceModel> myPlacesList;
    private List<MyPlaceModel> filteredList;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

        Toolbar toolbar = findViewById(R.id.toolbar_places);
        setSupportActionBar(toolbar);

        fabAdd = findViewById(R.id.fabAdd_myPlaces);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(getApplicationContext(), PlacesActivity.class);
                startActivity(h);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView_my_places);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize list and adapter
        myPlacesList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new MyPlacesAdapter(filteredList); // Initially show the filtered list
        recyclerView.setAdapter(adapter);

        // Fetch data from Firestore
        fetchPlaceNamesFromFirestore();


//        check which item from list is clicked and get and send its name to next activity
        // Set click listener for each item in the RecyclerView
        adapter.setOnItemClickListener(new MyPlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Get the clicked item from the list
                MyPlaceModel clickedPlace = myPlacesList.get(position);

                // Extract shot name from the clicked item
                String placeName = clickedPlace.getName();

                // Start the ShowShots activity and pass the shot name
                Intent intent = new Intent(getApplicationContext(), ShowPlaces.class);
                intent.putExtra("placeName", placeName);
                startActivity(intent);
            }
        });


    }


    private void fetchPlaceNamesFromFirestore() {
        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get user ID
            String userId = currentUser.getUid();

            // Query Firestore to fetch all documents in the "shots" collection for the current user
            db.collection("users").document(userId).collection("places")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Clear the list before adding new data
                            myPlacesList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                // Get the document ID (shot name)
                                String name = document.getId();

                                // Create a MyShotModel object with the shot name
                                MyPlaceModel myPlace = new MyPlaceModel(name);

                                // Add the MyShotModel object to the list
                                myPlacesList.add(myPlace);
                            }

                            // Initially, populate the filtered list with all shots
                            filteredList.clear();
                            filteredList.addAll(myPlacesList);
                            // Notify the adapter of the changes
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                            Toast.makeText(MyPlacesActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return true;
    }

    private void filter(String query) {
        filteredList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(myPlacesList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (MyPlaceModel place : myPlacesList) {
                if (place.getName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(place);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


}
