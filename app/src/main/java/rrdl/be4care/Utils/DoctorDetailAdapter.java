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
    String[] titles = {"Date de document", "Professionnel de santé", "Type de document"
            , "Structure de santé", "Lieu","Notes"};
    String[]data= {"Date de document", "Professionnel de santé", "Type de document"
            , "Structure de santé", "Lieu","Notes"};;
    public DoctorDetailAdapter(Context context,Doctor document) {
        try{
            mDoctor = document;
            mContext = context;
            /*data[0] = mDoctor.getFullName();
            data[1] = mDoctor.getDr();
            data[2] = mDoctor.getType();
            data[3] = mDoctor.getHStructure();
            data[4] = mDoctor.getPlace();
            data[5] = mDoctor.getNote();*/
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