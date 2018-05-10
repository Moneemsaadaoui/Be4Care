package rrdl.be4care.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.R;

public class DoctorListAdapter extends BaseAdapter {

    private List<Doctor> mDoctorList;
    private Context mContext;
    private ListView mListView;


    public DoctorListAdapter(Context context, List<Doctor> doctorList, ListView listView) {
        mContext = context;
        mDoctorList = doctorList;
        mListView = listView;
    }


    @Override
    public int getCount() {
        return mDoctorList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Doctor buffer;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View element = inflater.inflate(R.layout.repertoire_item, viewGroup, false);
        TextView title = view.findViewById(R.id.doctorlist);
        buffer = mDoctorList.get(i);
        title.setText(buffer.getFullName());
        return element;
    }
}
