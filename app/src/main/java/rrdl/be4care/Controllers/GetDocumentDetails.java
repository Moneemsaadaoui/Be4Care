package rrdl.be4care.Controllers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import rrdl.be4care.Models.Document;

public class GetDocumentDetails {
    private Context mContext;
    private Document mDocument;
    private TextView title;
    private ListView mListView;
    private ImageView mImageView;
    private TextView type, date, profs, structs, lieu, note;

    public GetDocumentDetails(Context context, Document document, ImageView image, TextView title
            , TextView type, TextView date, TextView profs, TextView structs,
                              TextView lieu, TextView note) {
        mDocument = document;
        mContext = context;
        mImageView = image;
        this.title = title;
        this.type = type;
        this.date = date;
        this.profs = profs;
        this.structs = structs;
        this.lieu = lieu;
        this.note = note;
    }

    public void getDetails() {
        Glide.with(mContext).load(mDocument.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
        title.setText( mDocument.getDr());
        date.setText(mDocument.getDate().substring(0, Math.min(mDocument.getDate().length(), 10)));
        profs.setText(mDocument.getDr());
        type.setText(mDocument.getType());
        structs.setText(mDocument.getHStructure());
        lieu.setText(mDocument.getPlace());
        note.setText(mDocument.getNote());


    }
}
