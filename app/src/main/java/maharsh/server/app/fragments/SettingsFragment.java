package maharsh.server.app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;

import maharsh.server.app.R;
import maharsh.server.app.preferences.CustomSwitchPreference;
import maharsh.server.app.preferences.DayNightSettings;

public class SettingsFragment extends PreferenceFragmentCompat {

    SharedPreferences.OnSharedPreferenceChangeListener spChanges;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        spChanges = (sharedPreferences, key) -> {
            try {
                if(key!=null){
                    if (key.equals("preference_night_mode"))
                        DayNightSettings.setDefaultMode(getContext());
                }
            }
            catch (Exception ignored){
            }

        };
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(spChanges);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(spChanges);
    }
}