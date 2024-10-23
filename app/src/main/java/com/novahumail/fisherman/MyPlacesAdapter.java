package com.novahumail.fisherman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyPlacesAdapter extends RecyclerView.Adapter<MyPlacesAdapter.ViewHolder> {

    private List<MyPlaceModel> myPlacesList;
    private OnItemClickListener mListener;

    // Define listener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyPlacesAdapter(List<MyPlaceModel> myPlacesList) {
        this.myPlacesList = myPlacesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_places, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyPlaceModel myPlace = myPlacesList.get(position);
        holder.nameTextView.setText(myPlace.getName());
        // Bind other attributes to corresponding views
    }

    @Override
    public int getItemCount() {
        return myPlacesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        // Declare other views for attributes

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.TV_places_item);
            // Initialize other views for attributes

            // Set click listener for the item
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }
}
