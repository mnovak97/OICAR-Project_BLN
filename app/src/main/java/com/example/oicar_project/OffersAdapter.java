package com.example.oicar_project;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;


import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private JsonPlaceHolderApi service;
    private List<OfferModel> offers;
    private LayoutInflater inflater;
    private OnItemClickedListener myOnItemClickedListener;
    private ListingModel listing;
    private List<ListingModel> listings;

    public OffersAdapter(List<OfferModel> offers, Context context, OnItemClickedListener onItemClickedListener, List<ListingModel> listings) {
        this.offers = offers;
        this.listings = listings;
        this.inflater = LayoutInflater.from(context);
        this.myOnItemClickedListener = onItemClickedListener;
    }

    public OffersAdapter(List<OfferModel> offers, Context context, OnItemClickedListener onItemClickedListener, ListingModel listing) {
        this.offers = offers;
        this.listing = listing;
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

        if (listing == null) {
            Optional<ListingModel> nullableListing = listings.stream().filter(listingModel -> listingModel.getIdListing() == offer.getListingId()).findFirst();
            if (nullableListing.isPresent()) {
                writeDetails(holder, offer, nullableListing.get());
            }

        } else {
            writeDetails(holder, offer, listing);
        }
    }

    private void writeDetails(OffersAdapter.ViewHolder holder, OfferModel offer, ListingModel listing) {

        holder.txtTitle.setText(listing.getTitle());
        holder.txtPrice.setText(String.format("%.2fHRK", offer.getPrice()));

        String tools;
        if (listing.isToolsRequired()) {
            tools = "Tools required | ";
            if (offer.isHasTools()) {
                tools += "I have my own tools";
            } else {
                tools += "I don't have tools";
                holder.txtTools.setTextColor(Color.RED);
            }
        } else {
            tools = "Tools not required";
        }
        holder.txtTools.setText(tools);

        if (!listing.isListed()) {
            if (offer.isAccepted()) {
                if (listing.isEmployeeReviewed() && listing.isEmployerReviewed()) {
                    holder.txtAccepted.setText("Completed");
                    holder.txtAccepted.setTextColor(Color.BLUE);
                } else {
                    holder.txtAccepted.setText("Accepted");
                    holder.txtAccepted.setTextColor(Color.GREEN);
                }
            } else {
                holder.txtAccepted.setText("Canceled");
                holder.txtAccepted.setTextColor(Color.RED);
            }
        } else {
            holder.txtAccepted.setText("Pending");
            holder.txtAccepted.setTextColor(Color.LTGRAY);
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
        TextView txtTitle;
        OnItemClickedListener onItemClickedListener;

        public ViewHolder(@NonNull View itemView, OnItemClickedListener onItemClickedListener) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtAccepted = itemView.findViewById(R.id.txtAccepted);
            txtTools = itemView.findViewById(R.id.txtToolsOffer);
            txtTitle = itemView.findViewById(R.id.txtListingTitle);
            this.onItemClickedListener = onItemClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickedListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }
}
