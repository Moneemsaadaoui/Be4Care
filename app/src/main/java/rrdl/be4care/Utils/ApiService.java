package rrdl.be4care.Utils;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Login;
import rrdl.be4care.Models.ProcessableDocument;
import rrdl.be4care.Models.SignupResponse;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.Models.Token;
import rrdl.be4care.Models.User;

public interface ApiService {
    //User Querries
    //POST METHODS

    // User

    @PATCH("/api/users/me")
    Call<User> updateuser(@Header("Authorization") String AuthToken, @Body JsonObject user);

    @GET("/api/users/me/exists")
    Call<Token> exist(@Header("Authorization") String AuthToken);
    //Authentication

    @POST("/api/users/login")
    Call<Login> login(@Body JsonObject jsonObject);

    @POST("/api/users/logout")
    Call<Login> logout();

    @POST("/api/users")
    Call<SignupResponse> signup(@Body JsonObject jsonObject);

    @GET("/api/users/me")
    Call<User> load_user(@Header("Authorization") String AuthToken);

    //Documents
    @POST("/api/documents/analyse")
    Call<JsonObject> analyse(@Header("Authorization") String AuthToken, @Body JsonObject jsonObject);

    @GET("/api/users/me/documents")
    Call<List<Document>> load_documents(@Header("Authorization") String AuthToken);


    @PATCH("/api/documents/{id}")
    Call<Document> updatedoc(@Header("Authorization") String AuthToken, @Body Document doc, @Path("id") String id);

    //Doctors
    @POST("/api/users/me/doctors")
    Call<Doctor> adddoctor(@Header("Authorization") String AuthToken, @Body Doctor doc);

    @POST("/api/users/me/documents")
    Call<ProcessableDocument> postDocument(@Header("Authorization") String AuthToken, @Body ProcessableDocument doc);

    @GET("/api/users/me/doctors")
    Call<List<Doctor>> load_doctors(@Header("Authorization") String AuthToken);

    @GET("/api/doctors")
    Call<List<Doctor>> getalldoctors(@Header("Authorization") String AuthToken);

    @GET("/api/users/me/doctors")
    Call<List<Doctor>> load_my_doctors(@Header("Authorization") String AuthToken);
    //HealthStruck

    @POST("/api/users/me/healthStructs")
    Call<JsonObject> addStruck(@Header("Authorization") String AuthToken, @Body Structure doc);

    @GET("/api/users/me/healthStructs")
    Call<List<Structure>> load_my_struck(@Header("Authorization") String AuthToken);


    @GET("/api/healthStructs")
    Call<List<Structure>> getalldstruck(@Header("Authorization") String AuthToken);
    //PATCH METHODS




    @DELETE("/api/users/me/documents/{id}")
    Call<JsonObject>delete_document(@Header("Authorization") String AuthToken,@Path("id")String id);
}
