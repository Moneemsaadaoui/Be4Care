package rrdl.be4care.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;

public class Getdocumentlist {
    private Context mContext;
    private List<Document> mDocuments;

    public Getdocumentlist(Context context) {
        mContext = context;
    }


    public List<Document> getdocuments() {
        try {
            if (InternalStorage.readObject(mContext, "DOCUMENTCACHE") != null) {
                mDocuments = (List<Document>) InternalStorage.readObject(mContext, "DOCUMENTCACHE");
                return mDocuments;
            } else {
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
                ApiService apiservice = retrofit.create(ApiService.class);
                Call<List<Document>> call = apiservice.load_documents(prefs.getString("AUTH", ""));
                call.enqueue(new Callback<List<Document>>() {
                    @Override
                    public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                        mDocuments = response.body();
                        try {
                            InternalStorage.writeObject(mContext, "DOCUMENTCACHE", mDocuments);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Document>> call, Throwable t) {
                        Toast.makeText(mContext, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                });
                return mDocuments;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            return mDocuments;
    }
}


