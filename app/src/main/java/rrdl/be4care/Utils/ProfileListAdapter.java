package rrdl.be4care.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import rrdl.be4care.R;
import rrdl.be4care.Views.Activities.ProfileActivity;

/**
 * Created by moneem on 18/04/18.
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    private String[] title = {"MonProfil", "Repertoire", "A Propos", "Mention legales", "contacts"};
    private Context mContext;


    private TypedArray icons;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profilerecycleritem, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,ProfileActivity.class);
                mContext.startActivity(intent);
            }
        });
        mContext = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfileListAdapter.ViewHolder holder, int position) {

        holder._title.setText(title[position]);
        switch (position) {
            case 0:
                holder._icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.file));
                break;

            case 1:
                holder._icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.phonebook));
                break;

            case 2:
                holder._icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.hand));
                break;

            case 3:
                holder._icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.file));
                break;

            case 4:
                holder._icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bubble));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return title.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _title;
        private ImageView _icon;

        public ViewHolder(final View itemView) {
            super(itemView);
            _title = itemView.findViewById(R.id.title);
            _icon = itemView.findViewById(R.id.icon);
            _icon.setScaleX(0.75f);//resize
            _icon.setScaleY(0.75f);
        }

    }
}
