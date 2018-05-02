package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Login;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Views.Activities.MainActivity;

public class Auth {


    private JsonObject user = new JsonObject();
    private Context mContext;
    private SharedPreferences pref;
    public Auth(Context context) {
        this.mContext = context;
    }


    public void Login(final CircularProgressButton button, EditText email, EditText password) {
        user.addProperty("username", email.getText().toString().trim());
        user.addProperty("password", password.getText().toString().trim());
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<Login> call = apiservice.login(user);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Toast.makeText(mContext, response.body().getId().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("TOKEN",response.body().getId());
                mContext.startActivity(intent);
                button.revertAnimation();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(mContext, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                button.revertAnimation();
            }
        });

    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}