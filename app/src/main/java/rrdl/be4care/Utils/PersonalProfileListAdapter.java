package rrdl.be4care.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import rrdl.be4care.Models.User;
import rrdl.be4care.R;

public class PersonalProfileListAdapter extends BaseAdapter {
    private Context mContext;
    private Gson userdata;
    private User user;
    List<String>data=null;
    String[] titles = {"Identifiant", "Nom", "Prénom", "Numero Telephone","Date de naissance"};
    String[] values1 = {"Identifiant", "Nom", "Prénom", "Numero Telephone","Date de naissance","Sexe"};
    String[] values;
    public PersonalProfileListAdapter(Context context,User user) {
        mContext = context;
        this.user = user;
        try {
            data.add(user.getEmail());
            data.add(user.getName());
            data.add(user.getLastName());
            data.add(user.getPhNumber());
            data.add(user.getBDate());

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();e.printStackTrace();}
        Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();
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
        TextView content = element.findViewById(R.id.InfoContent);

        title.setText(titles[i]);
        content.setText("");

        return element;
    }
}
