package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Utils.AllDoctorsAdapter;
import rrdl.be4care.Utils.ApiService;

public class GetallDocs {
    private Context mContext;
    private RecyclerView rv;
    private android.widget.SearchView sv;
    private AllDoctorsAdapter ada;
    public GetallDocs(Context context, RecyclerView rv, android.widget.SearchView sv) {
        mContext = context;
        this.rv = rv;
        this.sv = sv;
    }
    public AllDoctorsAdapter getadapter(){
        return ada;
    }
    public void getall() {
        SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Doctor>> get = apiservice.getalldoctors(prefs.getString("AUTH", ""));
        get.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
              final AllDoctorsAdapter ada = new AllDoctorsAdapter(mContext, response.body(),sv);
                Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                ada.getFilter();
                rv.setAdapter(ada);
                sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        ada.getFilter().filter(s);

                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {

            }
        });
    }
}