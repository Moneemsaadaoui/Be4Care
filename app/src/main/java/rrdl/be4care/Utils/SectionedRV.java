package rrdl.be4care.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.DocumentDetails;

public class SectionedRV extends SimpleSectionedAdapter<SectionedRV.ViewHolder> implements Filterable{
    private List<Document> response;
    private List<Document> responsefiltered;
    public LoadDocuments load;
    private Context mContext;
    private callback mCallback;
    private ArrayList<Document> responsecopy;
    int section,secpos;

    private List<String> datelist = new ArrayList<String>();
    private List<String> doclist = new ArrayList<String>();
    private List<String> strucklist = new ArrayList<String>();
    private List<String> typelist = new ArrayList<String>();
    private List<String> TitleList=new ArrayList<String>();
    private List<Integer> countlist = new ArrayList<Integer>();
    private int offset = 0;

    public SectionedRV(Context context, List<Document> body) {
        mContext = context;
        this.response = body;
        responsefiltered = this.response;
        SortBytype();


    }

    @SuppressLint("NewApi")
    public SectionedRV(Context context, List<Document> body, SearchView sv) {
        mContext = context;
        this.response = body;
        responsefiltered = this.response;
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
        TitleList.clear();
        SortBytype();


    }
    @Override
    protected String getSectionHeaderTitle(int section) {
            return TitleList.get(section);
    }

    @Override
    protected int getSectionCount() {
        return TitleList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return countlist.get(section);
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int section, int position) {
        holder._date.setText(responsefiltered.get(position).getDate()
                .substring(0, Math.min(responsefiltered.get(position).getDate().length(), 10)));
        holder._type.setText("test");
        // responsefiltered.get(positi ).getDr()
        holder._source.setText(responsefiltered
                .get(position).getHStructure() + " , " + responsefiltered.get(position).getPlace());
        if (responsefiltered.get(position).getStar()) {
            holder._star.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(responsefiltered.get(position).getUrl())
                .override(75, 75).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder._thumb);
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






    public void Sortbyname() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
        countlist.clear();
        TitleList.clear();
        Collections.sort(response, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDr().compareTo(t1.getDr());
            }
        });
        Collections.sort(responsefiltered, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDr().compareTo(t1.getDr());
            }
        });
        datelist.add(response.get(0).getDate());
        strucklist.add(response.get(0).getHStructure());
        doclist.add(response.get(0).getDr());

        for (Document doc : response) {
            boolean test = false;
            for (String curr : doclist) {
                if (doc.getDr().equals(curr)) {
                    test = true;
                }

            }
            if (test == false) doclist.add(doc.getDr());

        }
        for (String type : doclist) {
            int count = 0;
            for (Document docu : response) {
                if (type.equals(docu.getDr())) {
                    count++;
                }
            }
            countlist.add(count);
        }
        notifyDataSetChanged();
        Toast.makeText(mContext, doclist.size() + " doctors", Toast.LENGTH_SHORT).show();
        TitleList=doclist;
        notifyDataSetChanged();

    }

    public void SortBydate() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
        countlist.clear();
        TitleList.clear();

        Collections.sort(response, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDate().compareTo(t1.getDate());
            }
        });
        Collections.sort(responsefiltered, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getDate().compareTo(t1.getDate());
            }
        });

        datelist.add(response.get(0).getDate());
        strucklist.add(response.get(0).getHStructure());
        doclist.add(response.get(0).getDr());
        for (Document doc : response) {
            boolean test = false;
            for (String curr : datelist) {
                if (doc.getDate().equals(curr)) {
                    test = true;
                }

            }
            if (test == false) datelist.add(doc.getDate());

        }
        for (String type : datelist) {
            int count = 0;
            for (Document docu : response) {
                if (type.equals(docu.getDate())) {
                    count++;
                }
            }
            countlist.add(count);
        }
        notifyDataSetChanged();
        Toast.makeText(mContext, datelist.size() + " dates", Toast.LENGTH_SHORT).show();
        TitleList=datelist;
        notifyDataSetChanged();

    }

    public void SortBytype() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
        countlist.clear();
        TitleList.clear();

        Collections.sort(response, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getType().compareTo(t1.getType());
            }
        });
        Collections.sort(responsefiltered, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getType().compareTo(t1.getType());
            }
        });

        datelist.add(response.get(0).getDate());
        strucklist.add(response.get(0).getHStructure());
        doclist.add(response.get(0).getDr());
        typelist.add(response.get(0).getType());
        for (Document doc : response) {
            boolean test = false;

            for (String curr : typelist) {
                if (doc.getType().equals(curr)) {
                    test = true;
                }

            }
            if (test == false) typelist.add(doc.getType());

        }
        for (String type : typelist) {
            int count = 0;
            for (Document docu : response) {
                if (type.equals(docu.getType())) {
                    count++;
                }
            }
            countlist.add(count);
        }
        notifyDataSetChanged();
        Toast.makeText(mContext, typelist.size() + " Types", Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext, countlist.size() + " ", Toast.LENGTH_SHORT).show();
        TitleList=typelist;
        notifyDataSetChanged();

    }


    public void SortByHstruck() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
        countlist.clear();
        TitleList.clear();

        Collections.sort(response, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getHStructure().compareTo(t1.getHStructure());
            }
        });
        Collections.sort(responsefiltered, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document t1) {
                return document.getHStructure().compareTo(t1.getHStructure());
            }
        });

        datelist.add(response.get(0).getDate());
        strucklist.add(response.get(0).getHStructure());
        doclist.add(response.get(0).getDr());
        typelist.add(response.get(0).getType());
        for (Document doc : response) {
            boolean test = false;
            for (String curr : strucklist) {
                if (doc.getHStructure().equals(curr)) {
                    test = true;
                }

            }
            if (test == false) strucklist.add(doc.getHStructure());

        }
        for (String type : strucklist) {
            int count = 0;
            for (Document docu : response) {
                if (type.equals(docu.getHStructure())) {
                    count++;
                }
            }
            countlist.add(count);
        }
        notifyDataSetChanged();
        Toast.makeText(mContext, datelist.size() + " Strucks", Toast.LENGTH_SHORT).show();
        TitleList=strucklist;
        notifyDataSetChanged();

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charstring = charSequence.toString();
                if (charstring.isEmpty()) {
                    responsefiltered.clear();
                    responsefiltered = response;

                } else {
                    List<Document> filteredlist = new ArrayList<>();
                    for (Document row : response) {
                        if (row.getDr().toString().toLowerCase().contains(charstring.toLowerCase())) {
                            filteredlist.add(row);
                        }
                    }
                    responsefiltered = filteredlist;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = responsefiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                responsefiltered = (ArrayList<Document>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _date, _type, _source;
        private ImageView _thumb, _star;

        public ViewHolder(final View itemView) {
            super(itemView);
            _date = itemView.findViewById(R.id.date);
            _type = itemView.findViewById(R.id.type);
            _source = itemView.findViewById(R.id.source);
            _thumb = itemView.findViewById(R.id.documentthumb);
            _star = itemView.findViewById(R.id.star);
        }
    }
}
