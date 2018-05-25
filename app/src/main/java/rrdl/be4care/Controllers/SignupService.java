package rrdl.be4care.Controllers;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Login;
import rrdl.be4care.Models.SignupResponse;
import rrdl.be4care.Utils.ApiService;

public class SignupService {
    JsonObject New_user=new JsonObject();
    Context mContext;
    EditText email,password,phone;
    CircularProgressButton mCircularProgressButton;
    public SignupService(EditText email,EditText passEdit ,EditText phone,
                         CircularProgressButton button,
                          Context context){

    mContext=context;
    this.email=email;
    this.password=passEdit;
    this.phone=phone;
    mCircularProgressButton=button;

    }
    public void signup(){
        New_user.addProperty("email",this.email.getText().toString());
        New_user.addProperty("password",this.password.getText().toString());
        New_user.addProperty("phNumber",this.phone.getText().toString());
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final ApiService apiservice = retrofit.create(ApiService.class);
        Call<SignupResponse> call = apiservice.signup(New_user);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
            Auth auth=new Auth(mContext);
            auth.Login(mCircularProgressButton,email,password);
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(mContext, "Error 0x5", Toast.LENGTH_SHORT).show();
                mCircularProgressButton.revertAnimation();
            }


        });
    }

}
