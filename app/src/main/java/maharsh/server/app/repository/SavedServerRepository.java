package maharsh.server.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import maharsh.server.app.database.MainDatabase;
import maharsh.server.app.database.dao.MainDatabaseAccessObject;
import maharsh.server.app.datamodel.ServerTable;

public class SavedServerRepository {

    MainDatabaseAccessObject mainDatabaseAccessObject;

    public SavedServerRepository(Application application){
        MainDatabase mainDatabase = MainDatabase.getInstance(application);
        mainDatabaseAccessObject = mainDatabase.notificationDao();
    }

    public LiveData<List<ServerTable>> getSavedServers(){
        return mainDatabaseAccessObject.getSavedServers();
    }

    public void addServerToDB(ServerTable serverTable){
        new AddNotificationAsyncTask(mainDatabaseAccessObject).execute(serverTable);
    }

    private static class AddNotificationAsyncTask extends AsyncTask<ServerTable, Void, Void> {

        private final MainDatabaseAccessObject mainDatabaseAccessObject;

        private AddNotificationAsyncTask(MainDatabaseAccessObject mainDatabaseAccessObject) {
            this.mainDatabaseAccessObject = mainDatabaseAccessObject;
        }

        @Override
        protected Void doInBackground(ServerTable... notifications) {
            mainDatabaseAccessObject.addServerToDB(notifications[0]);
            return null;
        }
    }
}
