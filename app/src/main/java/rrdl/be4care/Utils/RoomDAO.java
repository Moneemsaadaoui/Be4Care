package rrdl.be4care.Utils;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.Models.User;

@Dao
public interface RoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertdocuments(List<Document> documents);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDoctors(List<Doctor> doctorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStruck(List<Structure> structureList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    /*  @Update
      void updatedocuments(List<Document> documents);

      @Update
      void updateDoctors(List<Doctor> doctorList);

      @Update
      void updateStruck(List<Structure> structureList);
  */
    @Query("SELECT * FROM Document")
    List<Document> getdocu();

    @Query("SELECT * FROM Structure")
    List<Structure> getStruck();

    @Query("SELECT * FROM Doctor")
    List<Doctor> getdoctors();

    @Query("DELETE  FROM Doctor")
    public void nukeDoctor();

    @Query("DELETE FROM Document")
    public void nukeDocument();

    @Query("DELETE FROM Structure")
    public void nukeStruck();

    @Query("SELECT * FROM User")
    User getuser();

    @Query("DELETE FROM Structure")
    public void nukeUser();

}
