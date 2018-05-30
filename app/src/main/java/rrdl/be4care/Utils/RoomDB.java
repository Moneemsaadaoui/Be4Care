package rrdl.be4care.Utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import rrdl.be4care.Models.Document;

@Database(entities = {Document.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase{
public abstract RoomDAO Dao();
}
