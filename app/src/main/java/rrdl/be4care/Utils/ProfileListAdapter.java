package rrdl.be4care.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import rrdl.be4care.R;

/**
 * Created by moneem on 18/04/18.
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder>{
    private String[] test={"MonProfil","Repertoire","A Propos","Mention legales","contacts"};
    private Context mContext;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profilerecycleritem, parent, false);
        mContext=parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfileListAdapter.ViewHolder holder, int position) {
    holder._title.setText(test[position]);
    holder._icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.file));
    }

    @Override
    public int getItemCount() {
        return test.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _title;
        private ImageView _icon;
        public ViewHolder(View itemView) {
            super(itemView);
            _title=itemView.findViewById(R.id.title);
            _icon=itemView.findViewById(R.id.icon);
            _icon.setScaleX(0.5f);//resize
            _icon.setScaleY(0.5f);
        }
    }
}
