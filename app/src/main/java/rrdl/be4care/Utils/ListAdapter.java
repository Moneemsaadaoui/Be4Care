package rrdl.be4care.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;

/**
 * Created by moneem on 18/04/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Document> response;
    public LoadDocuments load;
    private Context mContext;
    private callback mCallback;

    public ListAdapter(Context context, List<Document> response) {
        mContext = context;
        this.response = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
            holder._date.setText(response.get(position).getDate());
            holder._type.setText(response.get(position).getType());
            holder._source.setText(response.get(position).getHStructure());
        }


    @Override
    public int getItemCount() {
        return response.size();
    }

    /*@Override
    public void getDocument(List<Document> d) {
        response = d;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _date, _type, _source;

        public ViewHolder(View itemView) {
            super(itemView);
            _date = itemView.findViewById(R.id.date);
            _type = itemView.findViewById(R.id.type);
            _source = itemView.findViewById(R.id.source);
        }
    }


}
