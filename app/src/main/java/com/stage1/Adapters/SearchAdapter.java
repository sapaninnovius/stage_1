package com.stage1.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stage1.Activities.SearchDetailActivity;
import com.stage1.Models.ResponseSearch;
import com.stage1.R;
import com.stage1.Utils.PrefManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private final HashMap<String, Boolean> filter;
    private final HashMap<String, String> role;
    List<ResponseSearch.Datum> barList;
    List<ResponseSearch.Datum> barListFiltered;

    public SearchAdapter(List<ResponseSearch.Datum> barList, HashMap<String, Boolean> filter, HashMap<String, String> role) {
        this.barList = barList;
        this.barListFiltered = barList;
        this.filter = filter;
        this.role = role;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_search_name.setText(barListFiltered.get(i).getName());
        Glide.with(myViewHolder.img_search_item.getContext()).load(new PrefManager(myViewHolder.img_search_item.getContext()).getpath() + barListFiltered.get(i).getProfilePic()).into(myViewHolder.img_search_item);
    }

    @Override
    public int getItemCount() {
        return barListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    barListFiltered = barList;
                } else {
                    List<ResponseSearch.Datum> filteredList = new ArrayList<>();
                    for (ResponseSearch.Datum row : barList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) && filter.get(role.get(row.getRoleId() + ""))) {
                            filteredList.add(row);
                        }
                    }

                    barListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = barListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                barListFiltered = (List<ResponseSearch.Datum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_search_item;
        TextView txt_search_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_search_item = itemView.findViewById(R.id.img_search_item);
            txt_search_name = itemView.findViewById(R.id.txt_search_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id",barListFiltered.get(getLayoutPosition()).getId().toString());
                    jsonObject.addProperty("name",barListFiltered.get(getLayoutPosition()).getName().toString());
                    jsonObject.addProperty("roleid",barListFiltered.get(getLayoutPosition()).getRoleId().toString());
                    jsonObject.addProperty("address",barListFiltered.get(getLayoutPosition()).getAddress().toString());
                    /*
                    String id =
                            + ":" + barListFiltered.get(getLayoutPosition()).getName()
                            + ":" + barListFiltered.get(getLayoutPosition()).getRoleId()
                            + ":" + barListFiltered.get(getLayoutPosition()).getAddress();*/
                    Log.d("JsonObject",jsonObject.toString());

                    String user = jsonObject.toString();
                    /*try {
                        JsonParser parser = new JsonParser();
                        JsonObject json = (JsonObject) parser.parse(user);

                        Log.d("JsonObject",json.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    v.getContext().startActivity(new Intent(v.getContext(),SearchDetailActivity.class).putExtra("user",user));
                }
            });
        }
    }
}
