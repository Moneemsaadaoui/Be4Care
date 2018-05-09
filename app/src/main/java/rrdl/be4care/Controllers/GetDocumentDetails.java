package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.User;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.DocumentDetailAdapter;

public class GetDocumentDetails {
    private Context mContext;
    private Document mDocument;
    private TextView title;
    private ListView mListView;
    private ImageView mImageView;
    public GetDocumentDetails(Context context, Document document, ImageView image, TextView title, ListView list){
        mDocument=document;
        mContext=context;
        mListView=list;
        mImageView=image;
        this.title=title;
    }
    public void getDetails(){
            Glide.with(mContext).load(mDocument.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
        title.setText("Dr "+mDocument.getDr());
        DocumentDetailAdapter dda=new DocumentDetailAdapter(mContext,mDocument);
        mListView.setAdapter(dda);
    }
}
