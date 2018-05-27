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

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final int HEADER_VIEW = 0;
    private static final int CONTENT_VIEW = 1;
    private List<Document> response;
    private List<Document> responsefiltered;
    public LoadDocuments load;
    private Context mContext;
    private callback mCallback;
    private ArrayList<Document> responsecopy;

    private List<String> datelist = new ArrayList<String>();
    private List<String> doclist = new ArrayList<String>();
    private List<String> strucklist = new ArrayList<String>();
    private List<String> typelist = new ArrayList<String>();

    public ListAdapter(Context context, List<Document> body) {
        mContext = context;
        this.response = body;
        responsefiltered = this.response;

    }

    @SuppressLint("NewApi")
    public ListAdapter(Context context, List<Document> body, SearchView sv) {
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


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_item, parent, false);
        return new ViewHolder(itemView);*/
        int layoutRes = 0;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEADER_VIEW:
                layoutRes = R.layout.shortcut_header;
                View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
                holder= new ViewHolderHeader(view);
                break;
            case CONTENT_VIEW:
                layoutRes = R.layout.document_item;
                View view2= LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);

                holder= new ViewHolderContent(view2);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == HEADER_VIEW) {
            ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
            viewHolderHeader.title.setText("THIS IS A TEST");
        } else {

            ViewHolderContent viewHolderContent = (ViewHolderContent) holder;
            viewHolderContent._date.setText(responsefiltered.get(position - 1).getDate().substring(0, Math.min(responsefiltered.get(position - 1).getDate().length(), 10)));
            viewHolderContent._type.setText(responsefiltered.get(position - 1).getDr());
            viewHolderContent._source.setText(responsefiltered.get(position - 1).getHStructure() + " , " + responsefiltered.get(position - 1).getPlace());
            if (responsefiltered.get(position - 1).getStar()) {
                viewHolderContent._star.setVisibility(View.VISIBLE);
            }
            Glide.with(mContext).load(responsefiltered.get(position - 1).getUrl()).override(75, 75).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolderContent._thumb);
            viewHolderContent.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();

                    Intent intent = new Intent(mContext, DocumentDetails.class);
                    intent.putExtra("DOC_REF", gson.toJson(responsefiltered.get(position - 1)));
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent);
                }
            });

    }
}

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else {
            return CONTENT_VIEW;
        }

    }


    public void Sortbyname() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
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
        notifyDataSetChanged();
        Toast.makeText(mContext, doclist.size() + " doctors", Toast.LENGTH_SHORT).show();
    }

    public void SortBydate() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
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
        notifyDataSetChanged();
        Toast.makeText(mContext, datelist.size() + " dates", Toast.LENGTH_SHORT).show();

    }

    public void SortBytype() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
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
            if (test == false) datelist.add(doc.getType());

        }
        notifyDataSetChanged();
        Toast.makeText(mContext, datelist.size() + " Types", Toast.LENGTH_SHORT).show();

    }

    public void SortByHstruck() {
        datelist.clear();
        strucklist.clear();
        doclist.clear();
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
        notifyDataSetChanged();
        Toast.makeText(mContext, datelist.size() + " Strucks", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return responsefiltered.size() + 1;
    }

public class ViewHolderContent extends RecyclerView.ViewHolder {
    private TextView _date, _type, _source;
    private ImageView _thumb, _star;

    public ViewHolderContent(final View itemView) {
        super(itemView);
        _date = itemView.findViewById(R.id.date);
        _type = itemView.findViewById(R.id.type);
        _source = itemView.findViewById(R.id.source);
        _thumb = itemView.findViewById(R.id.documentthumb);
        _star = itemView.findViewById(R.id.star);
    }


}

public class ViewHolderHeader extends RecyclerView.ViewHolder {
    private TextView title;

    public ViewHolderHeader(final View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleHeader);
    }

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
}
