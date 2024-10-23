package com.novahumail.fisherman;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

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

public class MyShotsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyShotsAdapter adapter;
    private List<MyShotModel> myShotsList;
    private List<MyShotModel> filteredList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shots);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(getApplicationContext(), TakesActivity.class);
                startActivity(h);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView_my_takes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize list and adapter
        myShotsList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new MyShotsAdapter(filteredList); // Initially show the filtered list
        recyclerView.setAdapter(adapter);

        // Fetch data from Firestore
        fetchShotNamesFromFirestore();
    }

    // Method to fetch shot names from Firestore
    private void fetchShotNamesFromFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("users").document(userId).collection("shots")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            myShotsList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                String name = document.getId();
                                MyShotModel myShot = new MyShotModel(name);
                                myShotsList.add(myShot);
                            }
                            // Initially, populate the filtered list with all shots
                            filteredList.clear();
                            filteredList.addAll(myShotsList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                            Toast.makeText(MyShotsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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
            filteredList.addAll(myShotsList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (MyShotModel shot : myShotsList) {
                if (shot.getName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(shot);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}




















//package com.novahumail.fisherman;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyShotsActivity extends AppCompatActivity {
//    private FloatingActionButton fabAdd;
//
//    private RecyclerView recyclerView;
//    private MyShotsAdapter adapter;
//    private List<MyShotModel> myShotsList;
//    private FirebaseFirestore db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_shots);
//
//        fabAdd = findViewById(R.id.fabAdd);
//
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent h = new Intent(getApplicationContext(), TakesActivity.class);
//                startActivity(h);
//            }
//        });
//
//        recyclerView = findViewById(R.id.recyclerView_my_takes);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialize Firestore
//        db = FirebaseFirestore.getInstance();
//
//        // Initialize list and adapter
//        myShotsList = new ArrayList<>();
//        adapter = new MyShotsAdapter(myShotsList);
//        recyclerView.setAdapter(adapter);
//
//        // Fetch data from Firestore
//        fetchShotNamesFromFirestore();
//
//
////        check which item from list is clicked and get and send its name to next activity
//        // Set click listener for each item in the RecyclerView
//        adapter.setOnItemClickListener(new MyShotsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // Get the clicked item from the list
//                MyShotModel clickedShot = myShotsList.get(position);
//
//                // Extract shot name from the clicked item
//                String shotName = clickedShot.getName();
//
//                // Start the ShowShots activity and pass the shot name
//                Intent intent = new Intent(MyShotsActivity.this, ShowShots.class);
//                intent.putExtra("shotName", shotName);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//
//
//    private void fetchShotNamesFromFirestore() {
//        // Get current user
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            // Get user ID
//            String userId = currentUser.getUid();
//
//            // Query Firestore to fetch all documents in the "shots" collection for the current user
//            db.collection("users").document(userId).collection("shots")
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            // Clear the list before adding new data
//                            myShotsList.clear();
//                            for (DocumentSnapshot document : task.getResult()) {
//                                // Get the document ID (shot name)
//                                String name = document.getId();
//
//                                // Create a MyShotModel object with the shot name
//                                MyShotModel myShot = new MyShotModel(name);
//
//                                // Add the MyShotModel object to the list
//                                myShotsList.add(myShot);
//                            }
//                            // Notify the adapter of the changes
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            Log.e("Firestore", "Error getting documents: ", task.getException());
//                            Toast.makeText(MyShotsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }
//
//
//}
