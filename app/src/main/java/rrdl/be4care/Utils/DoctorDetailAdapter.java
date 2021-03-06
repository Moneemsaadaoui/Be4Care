package rrdl.be4care.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.User;
import rrdl.be4care.R;

public class DoctorDetailAdapter extends BaseAdapter {
    private Context mContext;
    private Doctor mDoctor;
    String[] titles = {"Adresse", "Numero de Telephone", "Adresse Email"
            , "Structure de santé"};
    String[]data = {"Adresse", "Numero de Telephone", "Adresse Email"
            , "Structure de santé"};
    public DoctorDetailAdapter(Context context,Doctor doctor) {
        try{
            mDoctor = doctor;
            mContext = context;

        }catch(Exception e){e.printStackTrace();}
    }


    @Override
    public int getCount() {
        return titles.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View element = inflater.inflate(R.layout.info_item, viewGroup, false);
        TextView title = element.findViewById(R.id.InfoTitle);
        TextView details = element.findViewById(R.id.InfoContent);
        //mPreview=element.findViewById(R.id.preview);
        //Glide.with(mContext).load(mDoctor.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPreview);
        title.setText(titles[i]);
        details.setText(data[i]);
        return element;
    }
}