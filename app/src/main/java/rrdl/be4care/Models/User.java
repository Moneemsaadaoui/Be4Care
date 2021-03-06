package rrdl.be4care.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity
public class User  {
    @SerializedName("name")
    @Expose
    @PrimaryKey
    @NonNull
    private String name;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phNumber")
    @Expose
    private String phNumber;
    @SerializedName("bDate")
    @Expose
    private String bDate;
    @SerializedName("sex")
    @Expose
    private Boolean sex;
    @SerializedName("pUrl")
    @Expose
    private String pUrl;
    @SerializedName("realm")
    @Expose
    @Ignore

    private String realm;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("emailVerified")
    @Expose

    private Boolean emailVerified;
    @SerializedName("Mydoctor")
    @Expose
    @Ignore

    private List<Doctor> mydoctor = null;
    @SerializedName("MhealthStruck")
    @Expose
    @Ignore
    private List<Structure> mhealthStruck = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getBDate() {
        return bDate;
    }

    public void setBDate(String bDate) {
        this.bDate = bDate;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPUrl() {
        return pUrl;
    }

    public void setPUrl(String pUrl) {
        this.pUrl = pUrl;
    }


    public void setRealm(String realm) {
        this.realm = realm;
    }

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

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
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
