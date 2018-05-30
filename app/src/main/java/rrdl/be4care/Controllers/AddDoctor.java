package rrdl.be4care.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Utils.ApiService;

public class AddDoctor {
    private Doctor mDoctor;
    private Context mContext;
    public AddDoctor(Context context,Doctor doctor){
        mDoctor=doctor;
        mContext=context;
    }
    public void add(){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "",
                "Chargement...", true);
        SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<Doctor>add=apiservice.adddoctor(prefs.getString("AUTH",""),mDoctor);
        add.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if(response.isSuccessful())
                {
                    dialog.dismiss();
                    Toast.makeText(mContext, "Medecin ajouté", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                    Toast.makeText(mContext, "Echec d'operation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {

            }
        });
    }
}
