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

import rrdl.be4care.Models.Structure;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.R;

public class AllStrcukAdapter extends RecyclerView.Adapter<AllStrcukAdapter.ViewHolder>
        implements Filterable {
    private Context mContext;
    private List<Structure> mStruck;
    private List<Structure> mStruckFiltered;
    private SearchView sv;

    public AllStrcukAdapter(Context context, List<Structure> struck, SearchView sv) {
        mContext = context;
        mStruck = struck;
        mStruckFiltered = mStruck;
        this.sv = sv;
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
    public AllStrcukAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repertoire_item_addable, parent, false);
        return new AllStrcukAdapter.ViewHolder(itemView);
    }

    

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mStruckFiltered.get(position).getFullName());
        if (mStruckFiltered.get(position).getStar()) {
            holder.star.setVisibility(View.VISIBLE);
        }
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mStruckFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charstring = charSequence.toString();
                if (charstring.isEmpty()) {
                    mStruckFiltered.clear();
                    mStruckFiltered = mStruck;

                } else {
                    List<Structure> filteredlist = new ArrayList<>();
                    for (Structure row : mStruck) {
                        if (row.getFullName().toLowerCase().contains(charstring.toLowerCase())) {
                            filteredlist.add(row);
                        }
                    }
                    mStruckFiltered = filteredlist;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mStruckFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mStruckFiltered = (ArrayList<Structure>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView star;
        Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doclistTitle);
            star = itemView.findViewById(R.id.fav);
            add = itemView.findViewById(R.id.adddoctor);

        }
    }
}
