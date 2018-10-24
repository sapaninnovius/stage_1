package com.stage1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    List<String> barList,barListFiltered;

    public SearchAdapter(List<String> barList) {
        this.barList = barList;
        this.barListFiltered = barList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return  new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_search_name.setText(barListFiltered.get(i).toString());
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
                    List<String> filteredList = new ArrayList<>();
                    for (String row : barList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
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
                barListFiltered = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_search_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_search_name = itemView.findViewById(R.id.txt_search_name);
        }
    }
}
