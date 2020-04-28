package com.example.oicar_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<Item> items;
    private LayoutInflater inflater;

    public BoardAdapter(List<Item> items, Context context) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.board_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.title.setText(item.title);
        holder.description.setText(item.description);
        holder.location.setText(item.loaction);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView location;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             title = itemView.findViewById(R.id.txtTitle);
             description = itemView.findViewById(R.id.txtDescription);
             location = itemView.findViewById(R.id.txtLocation);
         }
     }
}
