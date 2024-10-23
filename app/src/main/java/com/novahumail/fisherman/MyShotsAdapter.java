package com.novahumail.fisherman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyShotsAdapter extends RecyclerView.Adapter<MyShotsAdapter.ViewHolder> {

    private List<MyShotModel> myShotsList;
    private OnItemClickListener mListener;

    // Define listener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyShotsAdapter(List<MyShotModel> myShotsList) {
        this.myShotsList = myShotsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_shots, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyShotModel myShot = myShotsList.get(position);
        holder.nameTextView.setText(myShot.getName());
        // Bind other attributes to corresponding views
    }

    @Override
    public int getItemCount() {
        return myShotsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        // Declare other views for attributes

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.TV_shots_item);
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
