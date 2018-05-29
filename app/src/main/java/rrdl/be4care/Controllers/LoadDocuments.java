package rrdl.be4care.Controllers;

import android.content.Context;
import android.net.NetworkInfo;
import android.support.constraint.solver.Cache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.ListAdapter;
import rrdl.be4care.Utils.SectionedRV;

public class LoadDocuments {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private String TOKEN;
    private SectionedRV la;
    public static final String BASE_URL = "https://peaceful-forest-76384.herokuapp.com/";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    public LoadDocuments(Context context, String TOKEN, RecyclerView rv) {
        mContext = context;
        this.TOKEN = TOKEN;
        mRecyclerView = rv;
    }

    public void Load_Docs() {

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Document>> call = apiservice.load_documents(TOKEN);
        call.enqueue(new Callback<List<Document>>()

        {
            @Override
            public void onResponse
                    (Call<List<Document>> call, Response<List<Document>> response) {
                 la = new SectionedRV(mContext, response.body());
                    mRecyclerView.setAdapter(la);


                /*
              List<Document>doc_list=new ArrayList<Document>();
              doc_list=response.body();
              mCallback.getDocument(doc_list);
                Toast.makeText(mContext,""+ doc_list.size(), Toast.LENGTH_SHORT).show();*/
            }
            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                Toast.makeText(mContext, "Erreur", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public SectionedRV getDocadapter()
    {
        return la;
    }
}