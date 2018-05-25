package rrdl.be4care.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.DocumentDetails;

/**
 * Created by moneem on 18/04/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable{
    private List<Document> response;
    private List<Document> responsefiltered;
    public LoadDocuments load;
    private Context mContext;
    private callback mCallback;
    private ArrayList<Document>responsecopy;

    private List<String>datelist;
    private List<String>doclist;
    private List<String>strucklist;


    public ListAdapter(Context context, List<Document> body) {
        mContext = context;
        this.response = body;
        responsefiltered=this.response;

    }
    @SuppressLint("NewApi")
    public ListAdapter(Context context, List<Document> body, SearchView sv) {
            mContext = context;
            this.response = body;
            responsefiltered=this.response;
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                .inflate(R.layout.document_item, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {
        holder._date.setText(responsefiltered.get(position).getDate().substring(0, Math.min(responsefiltered.get(position).getDate().length(), 10)));
        holder._type.setText(responsefiltered.get(position).getDr());
        holder._source.setText(responsefiltered.get(position).getHStructure() + " , " + responsefiltered.get(position).getPlace());
        if(responsefiltered.get(position).getStar()) {
        holder._star.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(responsefiltered.get(position).getUrl()).override(75, 75).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder._thumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();

                Intent intent = new Intent(mContext, DocumentDetails.class);
                intent.putExtra("DOC_REF", gson.toJson(responsefiltered.get(position)));
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(intent);
            }
        });
    }
    public void Sortbyname()
    {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
        Collections.sort(response,new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDr().compareTo(t1.getDr());
            }
        });
        Collections.sort(responsefiltered,new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDr().compareTo(t1.getDr());
            }
        });
        datelist.add(response.get(0).getDate());
        strucklist.add(response.get(0).getHStructure());
        doclist.add(response.get(0).getDr());

        for(Document doc:response) {
            boolean test=false;
            for(String curr:doclist)
            {
                if(doc.getDr().equals(curr)){test=true;}

            }
            doclist.add(doc.getDr());
        notifyDataSetChanged();

        }
    }
    public void SortBydate()
    {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
        Collections.sort(response,new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDate().compareTo(t1.getDate());
            }
        });
        Collections.sort(responsefiltered,new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDate().compareTo(t1.getDate());
            }
        });

        datelist.add(response.get(0).getDate());
        strucklist.add(response.get(0).getHStructure());
        doclist.add(response.get(0).getDr());
        for(Document doc:response) {
            boolean test = false;
            for (String curr : datelist) {
                if (doc.getDate().equals(curr)) {
                    test = true;
                }

            }
            doclist.add(doc.getDate());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return responsefiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _date, _type, _source;
        private ImageView _thumb,_star;

        public ViewHolder(final View itemView) {
            super(itemView);
            _date = itemView.findViewById(R.id.date);
            _type = itemView.findViewById(R.id.type);
            _source = itemView.findViewById(R.id.source);
            _thumb = itemView.findViewById(R.id.documentthumb);
            _star=itemView.findViewById(R.id.star);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charstring=charSequence.toString();
                if(charstring.isEmpty()){
                    responsefiltered.clear();
                    responsefiltered=response;

                }else{
                    List<Document> filteredlist=new ArrayList<>();
                    for(Document row :response){
                        if(row.getDr().toString().toLowerCase().contains(charstring.toLowerCase()) )
                        {
                            filteredlist.add(row);
                        }
                    }
                    responsefiltered=filteredlist;

                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=responsefiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                responsefiltered = (ArrayList<Document>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
