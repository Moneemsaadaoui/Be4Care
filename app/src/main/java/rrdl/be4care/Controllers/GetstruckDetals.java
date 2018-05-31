package rrdl.be4care.Controllers;

import android.content.Context;
import android.media.Image;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Structure;

public class GetstruckDetals {
    private Context mContext;
    private Structure mStructure;
    private TextView title;
    private ListView mListView;
    private TextView address,numtel,email,sturct,spec;
    private ImageButton star;
    public GetstruckDetals(Context context, Structure document, TextView title,
                           TextView adress, TextView numtel, TextView email /*, ImageButton star*/) {
        this.address=adress;
        this.numtel=numtel;
        this.email=email;
        mStructure = document;
        mContext = context;
        this.title = title;
        //this.star=star;
    }

    public void getDetails() {
        title.setText(mStructure.getFullName());
        address.setText(mStructure.getAdress());
        numtel.setText(mStructure.getPhNumber());
        email.setText(mStructure.getEmail());
        //if(mStructure.getstar()){star.setDrawable...}
    }
}