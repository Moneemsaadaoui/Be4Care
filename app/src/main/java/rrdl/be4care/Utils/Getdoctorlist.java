package rrdl.be4care.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Doctor;

public class Getdoctorlist {
    private Context mContext;
    private List<Doctor> mDoctors;
    public Getdoctorlist(Context context)
    {
        mContext=context;
    }


    public List<Doctor> getdocuments() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Doctor>> docs = apiservice.load_doctors(prefs.getString("AUTH",""));
        docs.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                mDoctors = response.body();
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(mContext, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
        return mDoctors;
    }

}