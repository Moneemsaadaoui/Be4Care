package rrdl.be4care.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rrdl.be4care.R;

/**
 * Created by moneem on 18/04/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private String[] test={"this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test"};
    private String[] test2={"this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test"};
    private String[] test3={"this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test","this is a test"};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
    holder._date.setText(test[position]);
    holder._type.setText(test2[position]);
    holder._source.setText(test3[position]);
    }

    @Override
    public int getItemCount() {
        return test.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _date,_type,_source;
        public ViewHolder(View itemView) {
            super(itemView);
            _date=itemView.findViewById(R.id.date);
            _type=itemView.findViewById(R.id.type);
            _source=itemView.findViewById(R.id.source);
        }
    }
}
