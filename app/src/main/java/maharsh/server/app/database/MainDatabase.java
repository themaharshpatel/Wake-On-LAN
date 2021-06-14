package maharsh.server.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import maharsh.server.app.database.dao.MainDatabaseAccessObject;
import maharsh.server.app.datamodel.ServerTable;


@Database(entities = {ServerTable.class}, version = 1,exportSchema = false)

public abstract class MainDatabase extends RoomDatabase {
    private static MainDatabase instance;

    public static synchronized MainDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MainDatabase.class, "MainDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MainDatabaseAccessObject notificationDao();
}
