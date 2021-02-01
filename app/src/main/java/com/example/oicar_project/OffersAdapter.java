package com.example.oicar_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oicar_project.Model.OfferModel;


import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private List<OfferModel> offers;
    private LayoutInflater inflater;


    public OffersAdapter (List<OfferModel>offers,Context context){
        this.offers = offers;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.offer_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {
        OfferModel offer = offers.get(position);
        holder.txtPrice.setText(String.valueOf(offer.getPrice()));
        if (offer.isHasTools()){
            holder.txtTools.setText("Has his own tools");
        }
        else holder.txtTools.setText("Doesn't have his own tools");
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice;
        TextView txtTools;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtTools = itemView.findViewById(R.id.txtToolsOffer);
        }
    }
}
