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
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.RepertoireAdapter;

public class GetMyDoctors {
    private Context mContext;
    private RecyclerView list;
    private String Token;
    private SharedPreferences prefs;
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
        Call<List<Doctor>> get = apiservice.load_doctors(prefs.getString("AUTH",""));
        get.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                RepertoireAdapter repertoireAdapter=new RepertoireAdapter(mContext,response.body());
                list.setAdapter(repertoireAdapter);
                repertoireAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
