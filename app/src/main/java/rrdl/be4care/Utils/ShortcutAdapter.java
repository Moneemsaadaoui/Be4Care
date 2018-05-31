package rrdl.be4care.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.DoctorDetail;
import rrdl.be4care.Views.Activities.DocumentDetails;
import rrdl.be4care.Views.Activities.StruckDetailActivity;

public class ShortcutAdapter extends SimpleSectionedAdapter<ShortcutAdapter.ViewHolder> {
    private List<Document> mDocuments=new ArrayList<Document>();
    private List<Doctor> mDoctorList=new ArrayList<Doctor>();
    private List<Structure> mStructureList=new ArrayList<Structure>();
    private Context mContext;
    private Space pad;
    public ShortcutAdapter(Context context,List<Document> docs, List<Doctor> doctors, List<Structure> struck) {
        mContext=context;
        for (Document doc : docs) {
            if (doc.getStar()) {
                mDocuments.add(doc);
            }
        }
        for (Doctor doctor : doctors) {
            if (doctor.getStar()) {
                mDoctorList.add(doctor);
            }
        }
        for (Structure struc : struck) {
            if (struc.getStar()) {
                mStructureList.add(struc);
            }
        }
        Toast.makeText(context, "mDoc size"+mDocuments.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        switch (section) {
            case 0:
                return "Document";

            case 1:
                return "Medicin";

            case 2:
                return "Structure de Santé";

        }
        return "";
    }

    @Override
    protected int getSectionCount() {
        return 3;
    }

    @Override
    protected int getItemCountForSection(int section) {
        switch (section) {
            case 0:
                return mDocuments.size();

            case 1:
                return mDoctorList.size();

            case 2:
                return mStructureList.size();

        }
        return 0;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int section, int position) {
        if (section == 0) {
            Glide.with(mContext).load(mDocuments.get(position).getUrl())
                    .override(75, 75).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder._thumb);
            holder._date.setText(mDocuments.get(position).getDate());
            holder._type.setText(mDocuments.get(position).getType());
            holder._source.setText(mDocuments.get(position).getHStructure());
            if (mDocuments.get(position).getStar()) {
                holder._star.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();

                    Intent intent = new Intent(mContext, DocumentDetails.class);
                    intent.putExtra("DOC_REF", gson.toJson(mDocuments.get(position)));
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent);
                }
            });

        } else if (section == 1) {
            holder.toppad.setVisibility(View.VISIBLE);
            holder.pad.setVisibility(View.VISIBLE);
            holder._date.setVisibility(View.GONE);
            holder._thumb.setVisibility(View.GONE);
            holder._source.setVisibility(View.GONE);
            holder._star.setVisibility(View.VISIBLE);
            holder._type.setText(mDoctorList.get(position).getFullName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();

                    Intent intent = new Intent(mContext, DoctorDetail.class);
                    intent.putExtra("REF", gson.toJson(mDoctorList.get(position)));
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent);
                }
            });
        } else if (section == 2) {
            holder.pad.setVisibility(View.VISIBLE);
            holder.toppad.setVisibility(View.VISIBLE);
            holder._star.setVisibility(View.VISIBLE);
            holder._date.setVisibility(View.GONE);
            holder._thumb.setVisibility(View.GONE);
            holder._source.setVisibility(View.GONE);
            holder._type.setText(mStructureList.get(position).getFullName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();

                    Intent intent = new Intent(mContext, StruckDetailActivity.class);
                    intent.putExtra("STRREF", gson.toJson(mStructureList.get(position)));
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _date, _type, _source;
        private ImageView _thumb, _star;
        private Space pad,toppad;
        public ViewHolder(final View itemView) {
            super(itemView);
            toppad=itemView.findViewById(R.id.toppad);
            pad=itemView.findViewById(R.id.pad);
            _date = itemView.findViewById(R.id.date);
            _type = itemView.findViewById(R.id.type);
            _source = itemView.findViewById(R.id.source);
            _thumb = itemView.findViewById(R.id.documentthumb);
            _star = itemView.findViewById(R.id.star);
        }
    }
}
