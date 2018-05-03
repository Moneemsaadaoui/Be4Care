package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.User;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.PersonalProfileListAdapter;

public class GetUserInfo {
    private Context mContext;
    private ListView mListView;
    private String Token;
    private SharedPreferences prefs;
    public GetUserInfo(Context context, ListView list,String token){
        Token=token;
        mContext=context;
        mListView=list;
        prefs=context.getSharedPreferences("AUTH",Context.MODE_PRIVATE);
    }
    public void getUser() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        Call<User> get = apiservice.load_user(Token);
        get.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                PersonalProfileListAdapter ppla=new PersonalProfileListAdapter(mContext,response.body());
                mListView.setAdapter(ppla);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
