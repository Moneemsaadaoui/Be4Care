package rrdl.be4care.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.DocumentDetails;

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
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {
        holder._date.setText(response.get(position).getDate().substring(0, Math.min(response.get(position).getDate().length(), 10)));
        holder._type.setText(response.get(position).getDr());
        holder._source.setText(response.get(position).getHStructure() + " , " + response.get(position).getPlace());
        Glide.with(mContext).load(response.get(position).getUrl()).override(75, 75).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder._thumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Reservoir.init(mContext, 2048); //in bytes
                } catch (IOException e) {
                    //failure
                }
                try {
                    Reservoir.put("DOC_REF", response.get(position));
                    Toast.makeText(mContext, "done", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                }
                Gson gson=new Gson();

                Intent intent=new Intent(mContext,DocumentDetails.class);
                intent.putExtra("DOC_REF",gson.toJson(response.get(position)));
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(intent);
            }
        });
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
        private ImageView _thumb;

        public ViewHolder(final View itemView) {
            super(itemView);
            _date = itemView.findViewById(R.id.date);
            _type = itemView.findViewById(R.id.type);
            _source = itemView.findViewById(R.id.source);
            _thumb = itemView.findViewById(R.id.documentthumb);
        }
    }


}
