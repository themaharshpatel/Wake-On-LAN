package maharsh.server.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import maharsh.server.app.datamodel.ServerTable;
import maharsh.server.app.repository.SavedServerRepository;

public class SavedServerViewModel extends AndroidViewModel {

    SavedServerRepository savedServerRepository;

    public SavedServerViewModel(@NonNull Application application) {
        super(application);
        savedServerRepository =new SavedServerRepository(application);
    }

    public LiveData<List<ServerTable>> getSavedServers(){
        return savedServerRepository.getSavedServers();
    }

    public void addServerToDB(ServerTable serverTable){
        savedServerRepository.addServerToDB(serverTable);
    }
}
