package rrdl.be4care.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.User;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.PersonalProfileListAdapter;
import rrdl.be4care.Utils.RoomDB;
import rrdl.be4care.Utils.RoundedImageView;

public class GetUserInfo {
    private Context mContext;
    private ListView mListView;
    private String Token;
    private SharedPreferences prefs;
    private RoundedImageView mRoundedImageView;
    private RoomDB db;

    public GetUserInfo(Context context, ListView list, String token, RoundedImageView roundedImageView){
        Token=token;
        mContext=context;
        mRoundedImageView=roundedImageView;
        mListView=list;
        prefs=context.getSharedPreferences("AUTH",Context.MODE_PRIVATE);
        db=RoomDB.getINSTANCE(mContext);
    }
    public void getUser() {
        User user=db.Dao().getuser();
        if(user!=null && !user.getEmail().equals("")) {
            PersonalProfileListAdapter personalProfileListAdapter=new PersonalProfileListAdapter(mContext,user);
            mListView.setAdapter(personalProfileListAdapter);
        }
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
                Glide.with(mContext).load(response.body().getPUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).override(75,75).into(mRoundedImageView);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
