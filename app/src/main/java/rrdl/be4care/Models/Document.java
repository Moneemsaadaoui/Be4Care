package rrdl.be4care.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import io.realm.RealmObject;
@Entity
public class Document {
    @SerializedName("url")
    @Expose
    @PrimaryKey
    @NonNull
    private String url;
    @SerializedName("star")
    @Expose
    private Boolean star;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("dr")
    @Expose
    private String dr;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("HStructure")
    @Expose
    private String hStructure;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("usersId")
    @Expose
    private String usersId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHStructure() {
        return hStructure;
    }

    public void setHStructure(String hStructure) {
        this.hStructure = hStructure;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

}