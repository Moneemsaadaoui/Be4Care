package rrdl.be4care.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.R;

public class AllDoctorsAdapter extends RecyclerView.Adapter<AllDoctorsAdapter.ViewHolder>
implements Filterable{
    private Context mContext;
    private List<Doctor>mDoctors;
    private List<Doctor>mDoctorsfiltered;
    private SearchView sv;
    public AllDoctorsAdapter(Context context, List<Doctor> doctors, SearchView sv){
        mContext=context;
        mDoctors=doctors;
        mDoctorsfiltered=mDoctors;
        this.sv=sv;
        this.sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilter().filter(s);
                return false;
            }
        });
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repertoire_item, parent, false);
        return new AllDoctorsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDoctorsfiltered.get(position).getFullName());
        if(mDoctorsfiltered.get(position).getStar()){holder.star.setVisibility(View.VISIBLE);}
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctorsfiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
            String charstring=charSequence.toString();
            if(charstring.isEmpty()){
                mDoctorsfiltered.clear();
                mDoctorsfiltered=mDoctors;

            }else{
                List<Doctor> filteredlist=new ArrayList<>();
                for(Doctor row :mDoctors){
                    if(row.getFullName().toLowerCase().contains(charstring.toLowerCase()) || row.getSpecialite().toLowerCase().contains(charstring.toLowerCase()))
                    {
                        filteredlist.add(row);
                    }
                }
                mDoctorsfiltered=filteredlist;

            }
                FilterResults filterResults=new FilterResults();
                filterResults.values=mDoctorsfiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDoctorsfiltered = (ArrayList<Doctor>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;ImageView star;Button add;
        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.doclistTitle);
            star=itemView.findViewById(R.id.fav);
            add=itemView.findViewById(R.id.add);

        }
    }
}
