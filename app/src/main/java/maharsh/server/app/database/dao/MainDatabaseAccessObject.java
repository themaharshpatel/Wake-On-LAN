package maharsh.server.app.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import maharsh.server.app.datamodel.ServerTable;


@Dao
public interface MainDatabaseAccessObject {

    @Insert
    void addServerToDB(ServerTable serverTable);

    @Query("SELECT * FROM SavedServersTable ORDER BY id asc")
    LiveData<List<ServerTable>> getSavedServers();

}
