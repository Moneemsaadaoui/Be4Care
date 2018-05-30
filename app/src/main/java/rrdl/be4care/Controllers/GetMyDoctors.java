package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.RepertoireAdapter;
import rrdl.be4care.Utils.RepertoireListAdapter;

public class GetMyDoctors {
    private Context mContext;
    private RecyclerView list;
    private String Token;
    private SharedPreferences prefs;
    private List<Structure> mStructureList;
    private List<Doctor> mDoctorList;
    public GetMyDoctors(Context context, RecyclerView list){
        mContext=context;
        this.list=list;
        prefs=context.getSharedPreferences("GLOBAL",Context.MODE_PRIVATE);
    }
    public void getDoctors() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Doctor>> get = apiservice.load_my_doctors(prefs.getString("AUTH",""));
        get.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                mDoctorList=response.body();
                Call<List<Structure>>getstruck=apiservice.load_my_struck(prefs.getString("AUTH",""));
                getstruck.enqueue(new Callback<List<Structure>>() {
                    @Override
                    public void onResponse(Call<List<Structure>> call, Response<List<Structure>> response) {
                        mStructureList=response.body();
                        RepertoireListAdapter repertoireAdapter=new RepertoireListAdapter(mDoctorList,mStructureList);
                        list.setAdapter(repertoireAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Structure>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
