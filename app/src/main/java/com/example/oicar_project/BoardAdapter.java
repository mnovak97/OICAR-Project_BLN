package com.example.oicar_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oicar_project.Model.ListingModel;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<ListingModel> listings;
    private LayoutInflater inflater;
    private OnItemClickedListener myOnItemClickedListener;
    public BoardAdapter(List<ListingModel> listings, Context context,OnItemClickedListener onItemClickedListener) {
        this.listings = listings;
        this.inflater = LayoutInflater.from(context);
        this.myOnItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.board_item,parent,false);
        return new ViewHolder(view,myOnItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListingModel listing = listings.get(position);
        holder.title.setText(listing.getTitle());
        holder.description.setText(listing.getDescription());
        //zasada hardkodirano dok nemam lokacije jer inace baca null
        holder.location.setText("Lokacija");
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView location;
        OnItemClickedListener onItemClickedListener;
         public ViewHolder(@NonNull View itemView,OnItemClickedListener onItemClickedListener) {
             super(itemView);
             title = itemView.findViewById(R.id.txtTitle);
             description = itemView.findViewById(R.id.txtDescription);
             location = itemView.findViewById(R.id.txtLocation);
             this.onItemClickedListener = onItemClickedListener;
             itemView.setOnClickListener(this);
         }

        @Override
        public void onClick(View view) {
            onItemClickedListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickedListener{
        void onItemClick(int position);
    }

}
