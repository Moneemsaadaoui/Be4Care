package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

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

public class GetallFav {
    private Context mContext;
    private List<Document> documents;
    private List<Doctor> doctors;
    private List<Structure> struck;

    public GetallFav(Context context) {
        mContext = context;
    }

    public void GetFavs() {
        final SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final ApiService apiservice = retrofit.create(ApiService.class);
        Call<List<Document>> getdocuments = apiservice.load_documents(prefs.getString("AUTH", ""));
        getdocuments.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                documents=response.body();
                Call<List<Doctor>>getdoctors=apiservice.getalldoctors(prefs.getString("AUTH",""));
                getdoctors.enqueue(new Callback<List<Doctor>>() {
                    @Override
                    public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                        doctors=response.body();
                        Call<List<Structure>>getstruck=apiservice.getalldstruck(prefs.getString("AUTH",""));
                        getstruck.enqueue(new Callback<List<Structure>>() {
                            @Override
                            public void onResponse(Call<List<Structure>> call, Response<List<Structure>> response) {
                                struck=response.body();
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