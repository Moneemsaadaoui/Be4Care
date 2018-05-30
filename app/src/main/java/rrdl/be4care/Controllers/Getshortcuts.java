package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.ShortcutAdapter;

public class Getshortcuts {
    private List<Document> mDocuments;
    private List<Doctor> mDoctorList;
    private List<Structure> mStructureList;
    private Context mContext;
    private RecyclerView rv;
    public Getshortcuts(Context context, RecyclerView recyclerView) {
        mContext = context;
        rv=recyclerView;
    }

    public void get() {
        SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Document>> getdocs = apiservice.load_documents(prefs.getString("AUTH", ""));
        getdocs.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                mDocuments = response.body();
                Call<List<Doctor>> getdoctors = apiservice.load_my_doctors(prefs.getString("AUTH", ""));
                getdoctors.enqueue(new Callback<List<Doctor>>() {
                    @Override
                    public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                        mDoctorList = response.body();
                        Call<List<Structure>> getstruck = apiservice.load_my_struck(prefs.getString("AUTH", ""));
                        getstruck.enqueue(new Callback<List<Structure>>() {
                            @Override
                            public void onResponse(Call<List<Structure>> call, Response<List<Structure>> response) {
                                mStructureList=response.body();
                                ShortcutAdapter shortcutAdapter=new ShortcutAdapter(mContext,mDocuments,mDoctorList,mStructureList);
                                rv.setAdapter(shortcutAdapter);
                            }

                            @Override
                            public void onFailure(Call<List<Structure>> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Doctor>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {

            }
        });
    }
}
