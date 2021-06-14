package maharsh.server.app.preferences;

import android.app.Application;

public class Server extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DayNightSettings.setDefaultMode(this);
        // initialize Rudder SDK here
    }
}