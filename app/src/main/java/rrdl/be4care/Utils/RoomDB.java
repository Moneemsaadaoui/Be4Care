package rrdl.be4care.Utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import rrdl.be4care.Models.Document;

@Database(entities = {Document.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB INSTANCE;

    public abstract RoomDAO Dao();

    public static RoomDB getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "Document")
                            .allowMainThreadQueries().build();

        }
        return INSTANCE;
    }
    public static void destroyINSTANCE(){
        INSTANCE=null;
    }
}
