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
import rrdl.be4care.Models.Structure;
import rrdl.be4care.Utils.AllDoctorsAdapter;
import rrdl.be4care.Utils.AllStrcukAdapter;
import rrdl.be4care.Utils.ApiService;

public class GetallStruck {
    private Context mContext;
    private RecyclerView rv;
    private android.widget.SearchView sv;
    private AllStrcukAdapter ada;
    public GetallStruck(Context context, RecyclerView rv, android.widget.SearchView sv) {
        mContext = context;
        this.rv = rv;
        this.sv = sv;
    }
    public AllStrcukAdapter getadapter(){
        return ada;
    }
    public void getall() {
        SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Structure>> get = apiservice.getalldstruck(prefs.getString("AUTH", ""));
        get.enqueue(new Callback<List<Structure>>() {
            @Override
            public void onResponse(Call<List<Structure>> call, Response<List<Structure>> response) {
                final AllStrcukAdapter ada = new AllStrcukAdapter(mContext, response.body(),sv);
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
            public void onFailure(Call<List<Structure>> call, Throwable t) {

            }
        });

    }
}