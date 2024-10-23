package com.novahumail.fisherman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageView menuIV_slabs;
    Button newTakeBTN_Main,newPlaceBTN_Main;
    private boolean isMenuOpen = false; // Variable to track the menu state
    private PopupWindow popupWindow; // Declare PopupWindow as a class member


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        menuIV_slabs = findViewById(R.id.IV_menu_slabs);


        menuIV_slabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMenuOpen) {
                    showCustomMenu(v); // Open the menu
                } else {
                    closeCustomMenu(); // Close the menu
                }
            }
        });


        newTakeBTN_Main=findViewById(R.id.BTN_newTake_main);
        newPlaceBTN_Main=findViewById(R.id.BTN_newPlace_main);


        newTakeBTN_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RecordsActivity
                startActivity(new Intent(getApplicationContext(), TakesActivity.class));
            }
        });


        newPlaceBTN_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RecordsActivity
                startActivity(new Intent(getApplicationContext(), PlacesActivity.class));
            }
        });


    }


    // Method to close the custom menu
    private void closeCustomMenu() {
        // Dismiss the PopupWindow if it's open
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        // Update the menu state
        isMenuOpen = false;
    }

    // Method to show the custom menu
    private void showCustomMenu(View view) {
        // Create a PopupWindow with a custom layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customMenuView = inflater.inflate(R.layout.menu_item_layout, null);

        // Initialize buttons in the custom menu layout
        Button logoutButton = customMenuView.findViewById(R.id.BTN_logout_menuItemLayout);
        Button mainActivityButton = customMenuView.findViewById(R.id.BTN_mainActivity_menuItemLayout);
        Button myTakesButton = customMenuView.findViewById(R.id.BTN_myTakes_menuItemLayout);
        Button myPlacesButton = customMenuView.findViewById(R.id.BTN_myPlaces_menuItemLayout);
        Button contactButton = customMenuView.findViewById(R.id.BTN_contact_menuItemLayout);

        // Set click listeners for buttons
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout action
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish(); // Close MainActivity after logout
                closeCustomMenu(); // Close the menu after logout
            }
        });

        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity Page
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish(); // Close MainActivity after logout
                closeCustomMenu(); // Close the menu after logout
            }
        });

        myTakesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RecordsActivity
                startActivity(new Intent(getApplicationContext(), MyShotsActivity.class));
                closeCustomMenu(); // Close the menu after navigating to RecordsActivity
            }
        });


        myPlacesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RecordsActivity
                startActivity(new Intent(getApplicationContext(), MyPlacesActivity.class));
                closeCustomMenu(); // Close the menu after navigating to RecordsActivity
            }
        });


        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RecordsActivity
                startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                closeCustomMenu(); // Close the menu after navigating to RecordsActivity
            }
        });

        // Create a PopupWindow to display the custom menu
        popupWindow = new PopupWindow(customMenuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Show the PopupWindow
        popupWindow.showAsDropDown(view, 0, 0);

        // Update the menu state
        isMenuOpen = true;
    }


}