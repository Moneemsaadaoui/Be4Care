package rrdl.be4care.Utils;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rrdl.be4care.Models.Login;

public interface ApiService {
    @POST("/api/users/login")
    Call<Login>login(@Body JsonObject jsonObject);
}
