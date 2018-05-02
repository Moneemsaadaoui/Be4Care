package rrdl.be4care.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Login;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Views.Activities.LoginActivity;

public class Auth {


   private JsonObject dummy_json = new JsonObject();
    private Context mContext;
    public Auth(Context context)
    {
    this.mContext=context;
    }
    public void Login(final CircularProgressButton button) {
        dummy_json.addProperty("username","moneem");
        dummy_json.addProperty("password","helloworld");
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<Login>call=apiservice.login(dummy_json);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Toast.makeText(mContext, response.body().getId().toString(), Toast.LENGTH_SHORT).show();
                button.revertAnimation();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}