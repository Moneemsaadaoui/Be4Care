package rrdl.be4care.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Structure {
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("phNumber")
    @Expose
    private String phNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("star")
    @Expose
    private Boolean star;
    @SerializedName("id")
    @Expose
    private String id;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
