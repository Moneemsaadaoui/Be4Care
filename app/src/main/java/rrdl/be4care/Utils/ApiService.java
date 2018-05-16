package rrdl.be4care.Utils;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Login;
import rrdl.be4care.Models.Token;
import rrdl.be4care.Models.User;

public interface ApiService {
    //User Querries
    //POST METHODs
    @POST("/api/users/login")
    Call<Login> login(@Body JsonObject jsonObject);

    @POST("/api/users/logout")
    Call<Login> logout();

    @POST("/api/users")
    Call<Login> signup(@Body JsonObject jsonObject);

    @POST("/documents/analyse")
    Call<JsonObject> analyse(@Body JsonObject jsonObject);


    //GET METHODS
    @GET("/api/users/me/documents")
    Call<List<Document>> load_documents(@Header("Authorization") String AuthToken);

    @GET("/api/users/me/doctors")
    Call<List<Doctor>> load_doctors(@Header("Authorization") String AuthToken);

    @GET("/api/users/me")
    Call<User> load_user(@Header("Authorization") String AuthToken);

    @GET("/api/users/me/exists")
    Call<Token> exist(@Header("Authorization") String AuthToken);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    @GET("/api/doctors")
    Call<List<Doctor>>getalldoctors(@Header("Authorization") String AuthToken);

    @GET("/api/healthStructs")
    Call<List<Doctor>>getalldstruck(@Header("Authorization") String AuthToken);
    //PATCH METHODS

    @PATCH("/api/users/me")
    Call<User>updateuser(@Header("Authorization") String AuthToken,@Body JsonObject user);
}
