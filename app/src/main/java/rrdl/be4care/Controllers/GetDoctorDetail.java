package rrdl.be4care.Controllers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Utils.DoctorDetailAdapter;

public class GetDoctorDetail {
    private Context mContext;
    private Doctor mDoctor;
    private TextView title;
    private ListView mListView;
    private TextView address,numtel,email,sturct,spec;
    public GetDoctorDetail(Context context, Doctor document, TextView title,
    TextView adress,TextView numtel,TextView email,TextView sturct,TextView spec) {
        this.address=adress;
        this.numtel=numtel;
        this.email=email;
        this.sturct=sturct;
        this.spec=spec;
        mDoctor = document;
        mContext = context;
        this.title = title;
    }

    public void getDetails() {
        spec.setText(mDoctor.getSpecialite());
        title.setText(mDoctor.getFullName());
        address.setText(mDoctor.getAdress());
        numtel.setText(mDoctor.getPhNumber());
        email.setText(mDoctor.getEmail());
        sturct.setText(mDoctor.getHealthStruct());
    }
}


