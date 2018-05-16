package rrdl.be4care.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;

public class searchadapter extends BaseAdapter {
    private List<Document>mDocuments;
    private List<Doctor>mDoctors;
    private Context mContext;
    public searchadapter(Context context,List<Doctor>doctors,List<Document>documents){
    mContext=context;
    mDoctors=doctors;
    mDocuments=documents;

    }    @Override
    public int getCount() {
    return 10;
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row;
        row = inflater.inflate(R.layout.document_item, (ViewGroup) viewGroup.getParent(), false);
        TextView date=row.findViewById(R.id.date);
        TextView type=row.findViewById(R.id.type);
        TextView source=row.findViewById(R.id.source);
        return row;
    }

}
