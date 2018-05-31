package rrdl.be4care.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.DoctorDetail;
import rrdl.be4care.Views.Activities.StruckDetailActivity;

public class RepertoireListAdapter extends SimpleSectionedAdapter<RepertoireListAdapter.ViewHolder> {
    private List<Doctor>mDoctorList;
    private List<Structure>mStructureList;
    private Context mContext;

    public RepertoireListAdapter(Context context,List<Doctor> doctor, List<Structure>struck)
    {
        mContext=context;
    mDoctorList=doctor;
    mStructureList=struck;
    }
    @Override
    protected String getSectionHeaderTitle(int section) {
       if(section==0){return "Medicin";}
       return "Structure De santé";
    }

    @Override
    protected int getSectionCount() {
        return 2;
    }

    @Override
    protected int getItemCountForSection(int section) {
        if (section==0){return mDoctorList.size();}
        else return mStructureList.size();
    }

    @Override
    protected RepertoireListAdapter.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repertoire_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(RepertoireListAdapter.ViewHolder holder, int section, int position) {
    if(section==0){
        if(mDoctorList.get(position).getStar()){
            holder.star.setVisibility(View.VISIBLE);
        }
        holder.name.setText(mDoctorList.get(position).getFullName());

        holder.itemView.setOnClickListener(view -> {
            Gson gson = new Gson();

            Intent intent = new Intent(mContext, DoctorDetail.class);
            intent.putExtra("REF", gson.toJson(mDoctorList.get(position)));
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent);
        });
    }else if(section==1){
        if(mStructureList.get(position).getStar()){
            holder.star.setVisibility(View.VISIBLE);
        }
        holder.name.setText(mStructureList.get(position).getFullName());

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
        private ImageView star;
        private TextView name;
        private Button add;
        public ViewHolder(View itemView) {
            super(itemView);
            star=itemView.findViewById(R.id.fav);
            name=itemView.findViewById(R.id.doclistTitle);

        }
    }
}
