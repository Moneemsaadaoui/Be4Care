package rrdl.be4care.Controllers;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Token;
import rrdl.be4care.Utils.ApiService;

public class TokenCheck {
    private Context mContext;
    private String conToken;
    public TokenCheck(Context context,String token){
        mContext=context;
        conToken=token;
    }
    public Boolean CheckToken() {
        final Boolean[] result = {false};
        String Token = mContext.getSharedPreferences("TOKEN", Context.MODE_PRIVATE).getString("TOKEN", "");
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final ApiService apiservice = retrofit.create(ApiService.class);
        Call<rrdl.be4care.Models.Token> check = apiservice.exist(conToken);
        check.enqueue(new Callback<rrdl.be4care.Models.Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
            result[0] =response.body().getExists();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
        return result[0];
    }
}
