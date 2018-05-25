package rrdl.be4care.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignupResponse {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Mydoctor")
    @Expose
    private List<Doctor> mydoctor = null;
    @SerializedName("MhealthStruck")
    @Expose
    private List<Structure> mhealthStruck = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Doctor> getMydoctor() {
        return mydoctor;
    }

    public void setMydoctor(List<Doctor> mydoctor) {
        this.mydoctor = mydoctor;
    }

    public List<Structure> getMhealthStruck() {
        return mhealthStruck;
    }

    public void setMhealthStruck(List<Structure> mhealthStruck) {
        this.mhealthStruck = mhealthStruck;
    }

}