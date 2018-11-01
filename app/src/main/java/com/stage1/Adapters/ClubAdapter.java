package com.stage1.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.stage1.Activities.SearchDetailActivity;
import com.stage1.Models.ResponseGetAllClub;
import com.stage1.Models.ResponseGetAllClub;
import com.stage1.R;
import com.stage1.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder>/* implements Filterable */{
   /* private final HashMap<String, Boolean> filter;
    private final HashMap<String, String> role;*/
    List<ResponseGetAllClub.Datum> barList;
//    List<ResponseGetAllClub.Datum> barList;

    public ClubAdapter(List<ResponseGetAllClub.Datum> barList/*, HashMap<String, Boolean> filter, HashMap<String, String> role*/) {
        this.barList = barList;
     /*   this.barList = barList;
        this.filter = filter;
        this.role = role;*/
    }

    @NonNull
    @Override
    public ClubAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ClubAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.club_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClubAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_club_name.setText(barList.get(i).getName());
        myViewHolder.txt_club_address.setText(barList.get(i).getAddress());
        myViewHolder.txt_club_number.setText(barList.get(i).getPhone());
//        Glide.with(myViewHolder.img_search_item.getContext()).load(new PrefManager(myViewHolder.img_search_item.getContext()).getpath() + barList.get(i).getProfilePic()).into(myViewHolder.img_search_item);
    }

    @Override
    public int getItemCount() {
        return barList.size();
    }

  /*  @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    barList = barList;
                } else {
                    List<ResponseGetAllClub.Datum> filteredList = new ArrayList<>();
                    for (ResponseGetAllClub.Datum row : barList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) && filter.get(role.get(row.getRoleId() + ""))) {
                            filteredList.add(row);
                        }
                    }

                    barList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = barList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                barList = (List<ResponseGetAllClub.Datum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }*/

    class MyViewHolder extends RecyclerView.ViewHolder {
        
        TextView txt_club_name,txt_club_address,txt_club_number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_club_name = itemView.findViewById(R.id.txt_club_name);
            txt_club_address = itemView.findViewById(R.id.txt_club_address);
            txt_club_number = itemView.findViewById(R.id.txt_club_number);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id",barList.get(getLayoutPosition()).getId().toString());
                    jsonObject.addProperty("name",barList.get(getLayoutPosition()).getName().toString());
                    jsonObject.addProperty("roleid",barList.get(getLayoutPosition()).getRoleId().toString());
                    jsonObject.addProperty("address",barList.get(getLayoutPosition()).getAddress().toString());
                    *//*
                    String id =
                            + ":" + barList.get(getLayoutPosition()).getName()
                            + ":" + barList.get(getLayoutPosition()).getRoleId()
                            + ":" + barList.get(getLayoutPosition()).getAddress();*//*
                    Log.d("JsonObject",jsonObject.toString());

                    String user = jsonObject.toString();
                    *//*try {
                        JsonParser parser = new JsonParser();
                        JsonObject json = (JsonObject) parser.parse(user);

                        Log.d("JsonObject",json.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*//*
                    v.getContext().startActivity(new Intent(v.getContext(),SearchDetailActivity.class).putExtra("user",user));
                }
            });*/
        }
    }
}
