package rrdl.be4care.Utils;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Login;

public interface ApiService {
    @POST("/api/users/login")
    Call<Login>login(@Body JsonObject jsonObject);
    @GET("/api/users/me/")
    Call<List<Document>>load_documents(@Header("Authorization")String AuthToken);
}
