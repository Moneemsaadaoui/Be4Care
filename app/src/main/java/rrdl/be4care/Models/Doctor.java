package rrdl.be4care.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;
@Entity
public class Doctor extends RealmObject {

    @SerializedName("fullName")
    @Expose
    @PrimaryKey
    @NonNull
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
    @SerializedName("healthStruct")
    @Expose
    private String healthStruct;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("specialite")
    @Expose
    private String specialite;
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

    public String getHealthStruct() {
        return healthStruct;
    }

    public void setHealthStruct(String healthStruct) {
        this.healthStruct = healthStruct;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
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
