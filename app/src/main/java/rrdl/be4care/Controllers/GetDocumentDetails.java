package rrdl.be4care.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;

public class GetDocumentDetails {
    private Context mContext;
    private Document mDocument;
    private TextView title;
    private ListView mListView;
    private ImageView mImageView;
    private TextView type, date, profs, structs, lieu, note;
    private ImageButton favicon;

    public GetDocumentDetails(Context context, Document document, ImageView image, TextView title
            , TextView type, TextView date, TextView profs, TextView structs,
                              TextView lieu, TextView note , ImageButton favicon) {
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
        this.favicon=favicon;
    }

    public void getDetails() {
        SharedPreferences prefs=mContext.getSharedPreferences("GLOBAL",Context.MODE_PRIVATE);
        Glide.with(mContext).load(mDocument.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
        title.setText( mDocument.getDr());
        date.setText(mDocument.getDate().substring(0, Math.min(mDocument.getDate().length(), 10)));
        profs.setText(mDocument.getDr());
        type.setText(mDocument.getType());
        structs.setText(mDocument.getHStructure());
        lieu.setText(mDocument.getPlace());
        String notebuffer=mDocument.getNote().replaceAll("\\\n","")
                .replaceAll("\\{","").replaceAll("\\}","").replaceAll("\\\r","");
        note.setText(notebuffer);
        if(mDocument.getStar()){favicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.star));}
        else{favicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unstar));
        favicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = ProgressDialog.show(mContext, "",
                        "Chargement...", true);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                final ApiService apiservice = retrofit.create(ApiService.class);
                Document tempDoc=mDocument;
                if(tempDoc.getStar()){tempDoc.setStar(false);}else{tempDoc.setStar(true);}
                Call<Document>update=apiservice.updatedoc(prefs.getString("AUTH",""),tempDoc,mDocument.getUsersId());
                update.enqueue(new Callback<Document>() {
                    @Override
                    public void onResponse(Call<Document> call, Response<Document> response) {
                        toggle(favicon);
                            dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Document> call, Throwable t) {
                    dialog.dismiss();
                    }
                });
            }
        });}


    }
    public void toggle(ImageButton fav){
        if(fav.getDrawable()==mContext.getResources().getDrawable(R.drawable.star)){
            fav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unstar));}

        else{fav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.star));}
    }
}
