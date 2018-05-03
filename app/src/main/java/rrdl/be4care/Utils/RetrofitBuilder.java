package rrdl.be4care.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public Retrofit build(){
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    return retrofit;}

}
