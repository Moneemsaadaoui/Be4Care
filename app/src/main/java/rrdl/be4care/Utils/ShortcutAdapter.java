package rrdl.be4care.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.R;

public class ShortcutAdapter extends SimpleSectionedAdapter<ShortcutAdapter.ViewHolder> {
    private List<Document> mDocuments=new ArrayList<Document>();
    private List<Doctor> mDoctorList=new ArrayList<Doctor>();
    private List<Structure> mStructureList=new ArrayList<Structure>();
    private Context mContext;

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

        } else if (section == 1) {
            holder._date.setVisibility(View.GONE);
            holder._thumb.setVisibility(View.GONE);
            holder._source.setVisibility(View.GONE);
            holder._star.setVisibility(View.VISIBLE);
            holder._type.setText(mDoctorList.get(position).getFullName());
        } else if (section == 2) {
            holder._star.setVisibility(View.VISIBLE);
            holder._date.setVisibility(View.GONE);
            holder._thumb.setVisibility(View.GONE);
            holder._source.setVisibility(View.GONE);
            holder._type.setText(mStructureList.get(position).getFullName());
        }
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
