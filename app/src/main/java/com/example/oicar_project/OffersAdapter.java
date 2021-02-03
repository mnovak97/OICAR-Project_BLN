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
    private OnItemClickedListener myOnItemClickedListener;

    public OffersAdapter(List<OfferModel> offers, Context context, OnItemClickedListener onItemClickedListener) {
        this.offers = offers;
        this.inflater = LayoutInflater.from(context);
        this.myOnItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.offer_layout, parent, false);
        return new ViewHolder(view, myOnItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {
        OfferModel offer = offers.get(position);
        holder.txtPrice.setText(String.valueOf(offer.getPrice()));
        if (offer.isHasTools()) {
            holder.txtTools.setText("Has his own tools");
        } else holder.txtTools.setText("Doesn't have his own tools");
        if (offer.isAccepted()) {
            holder.txtAccepted.setText("Accepted");
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtPrice;
        TextView txtAccepted;
        TextView txtTools;
        OnItemClickedListener onItemClickedListener;

        public ViewHolder(@NonNull View itemView, OnItemClickedListener onItemClickedListener) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtAccepted = itemView.findViewById(R.id.txtAccepted);
            txtTools = itemView.findViewById(R.id.txtToolsOffer);
            this.onItemClickedListener = onItemClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickedListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
}
