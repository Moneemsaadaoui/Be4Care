package rrdl.be4care.Controllers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Utils.DoctorDetailAdapter;

public class GetDoctorDetail {
    private Context mContext;
    private Doctor mDoctor;
    private TextView title;
    private ListView mListView;

    public GetDoctorDetail(Context context, Doctor document, TextView title, ListView list) {
        mDoctor = document;
        mContext = context;
        mListView = list;
        this.title = title;
    }

    public void getDetails() {
        title.setText("Dr " + mDoctor.getFullName());
        DoctorDetailAdapter dda = new DoctorDetailAdapter(mContext, mDoctor);
        mListView.setAdapter(dda);
    }
}


