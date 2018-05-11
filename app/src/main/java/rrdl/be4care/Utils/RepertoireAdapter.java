package rrdl.be4care.Utils;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.List;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.User;
import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.DoctorDetail;
import rrdl.be4care.Views.Activities.DocumentDetails;
import rrdl.be4care.Views.Fragments.Repertoire;

public class RepertoireAdapter extends RecyclerView.Adapter<RepertoireAdapter.ViewHolder> {
    private List<Doctor> response;
    private Context mContext;
    private callback mCallback;

    public RepertoireAdapter(Context context, List<Doctor> response) {
        mContext = context;
        this.response = response;
        Toast.makeText(context, this.response.size() + " as a size", Toast.LENGTH_SHORT).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repertoire_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepertoireAdapter.ViewHolder holder, final int position) {
            holder._title.setText(response.get(position).getFullName());
            if(response.get(position).getStar()){
                holder._imgview.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();

                    Intent intent = new Intent(mContext, DoctorDetail.class);
                    intent.putExtra("DOCTOR", gson.toJson(response.get(position)));
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return response.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _title;
        private ImageView _imgview;
        public ViewHolder(final View itemView) {
            super(itemView);
            _title = itemView.findViewById(R.id.doclistTitle);
            _imgview=itemView.findViewById(R.id.fav);
        }
    }
}
    /*public RepertoireAdapter(Context context, List<Doctor> doctors) {
        super();
        mContext = context;
        this.doctors = doctors;
    }


    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View element = inflater.inflate(R.layout.repertoire_item, viewGroup, false);
        TextView title = element.findViewById(R.id.Title);
        ImageView fav=element.findViewById(R.id.fav);
        buffer=doctors.get(i);
        title.setText( buffer.getFullName().toString());
        if(buffer.getStar()==true){fav.setVisibility(View.VISIBLE);}
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,DoctorDetail.class);
                Gson gson=new Gson();
                intent.putExtra("DOCREF",gson.toJson(doctors.get(i)));
                mContext.startActivity(intent);

            }
        });
        return element;
    }
}
 /*  public void click(View view, final int i) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, i + "", Toast.LENGTH_SHORT).show();
            }
        });

    }*/