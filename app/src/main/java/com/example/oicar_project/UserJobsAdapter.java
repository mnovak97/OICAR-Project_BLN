package com.example.oicar_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJobsAdapter extends RecyclerView.Adapter<UserJobsAdapter.ViewHolder> {

    private List<ListingModel> userListings;
    private LayoutInflater inflater;
    private JsonPlaceHolderApi service;
    private List<OfferModel> offers;
    private OnItemClickedListener myOnItemClickedListener;

    public UserJobsAdapter(List<ListingModel> userListings, Context context,OnItemClickedListener onItemClickedListener){
        this.userListings = userListings;
        this.inflater = LayoutInflater.from(context);
        this.myOnItemClickedListener = onItemClickedListener;
    }


    @NonNull
    @Override
    public UserJobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_job,parent,false);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        return new ViewHolder(view,myOnItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserJobsAdapter.ViewHolder holder, int position) {
        ListingModel userJob = userListings.get(position);
        holder.txtTitleUserJob.setText(userJob.getTitle());
        holder.txtDescriptionUserJob.setText(userJob.getDescription());
        Call<List<OfferModel>> offerModelCall = service.getOffersForListingId(userJob.getIdListing());
        offerModelCall.enqueue(new Callback<List<OfferModel>>() {
            @Override
            public void onResponse(Call<List<OfferModel>> call, Response<List<OfferModel>> response) {
                offers = response.body();
                if(offers.size() > 0)
                {
                    holder.txtNumberOfOffers.setText(String.valueOf(offers.size()));
                }
                else holder.txtNumberOfOffers.setText("");

            }

            @Override
            public void onFailure(Call<List<OfferModel>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userListings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitleUserJob;
        TextView txtDescriptionUserJob;
        TextView txtNumberOfOffers;
        OnItemClickedListener onItemClickedListener;
        public ViewHolder(@NonNull View itemView,OnItemClickedListener onItemClickedListener) {
            super(itemView);
            txtTitleUserJob = itemView.findViewById(R.id.txtTitleUserJob);
            txtDescriptionUserJob = itemView.findViewById(R.id.txtDescriptionUserJob);
            txtNumberOfOffers = itemView.findViewById(R.id.txtNumberOfOffers);
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
