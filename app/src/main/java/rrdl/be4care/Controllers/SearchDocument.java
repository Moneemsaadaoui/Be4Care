package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.ListAdapter;
import rrdl.be4care.Utils.RoomDB;
import rrdl.be4care.Utils.SectionedRV;

public class SearchDocument {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private String TOKEN;
    private SearchView sv;
    public SearchDocument(Context context, RecyclerView rv, SearchView sv) {
        mContext=context;
        mRecyclerView=rv;
        this.sv=sv;
    }

    public  void Load_Docs() {
        RoomDB db= RoomDB.getINSTANCE(mContext);

        List<Document> cacheddocs=db.Dao().getdocu();

        if(cacheddocs!=null && cacheddocs.size()!=0) {
            SectionedRV cacheload = new SectionedRV(mContext, cacheddocs);
            mRecyclerView.setAdapter(cacheload);
        }
        SharedPreferences prefs=mContext.getSharedPreferences("GLOBAL",Context.MODE_PRIVATE);
        TOKEN=prefs.getString("AUTH","");
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Document>> call = apiservice.load_documents(TOKEN);
        call.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                final ListAdapter la=new ListAdapter(mContext,response.body(),sv);
                Toast.makeText(mContext, response.body().size()+" siize", Toast.LENGTH_SHORT).show();
                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        la.getFilter().filter(s);
                        return false;
                    }
                });
                mRecyclerView.setAdapter(la);


                /*
              List<Document>doc_list=new ArrayList<Document>();
              doc_list=response.body();
              mCallback.getDocument(doc_list);
                Toast.makeText(mContext,""+ doc_list.size(), Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                Toast.makeText(mContext,"Erreur", Toast.LENGTH_SHORT).show();
                if(cacheddocs!=null && cacheddocs.size()!=0) {
                    ListAdapter cacheload = new ListAdapter(mContext, cacheddocs,sv);
                    mRecyclerView.setAdapter(cacheload);
                }
            }
        });

    }

}