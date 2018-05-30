package rrdl.be4care.Utils;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rrdl.be4care.Models.Document;

@Dao
public interface RoomDAO {
    @Update
    void insertdocuments(List<Document> documents);

    @Query("SELECT * FROM Document")
    List<Document> getdocu();
}
