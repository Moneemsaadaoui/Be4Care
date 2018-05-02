package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Login;
import rrdl.be4care.Utils.ApiService;

public class LoadDocuments {
    private Context mContext;

    public LoadDocuments(Context context) {
        this.mContext = context;

    }

    public void Load_Docs() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Document>> call = apiservice.load_documents(mContext.
                getSharedPreferences("TOKEN", Context.MODE_PRIVATE).getString("TOKEN", null));
        call.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                Toast.makeText(mContext,"Erreur", Toast.LENGTH_SHORT).show();

            }
        });

    }
}