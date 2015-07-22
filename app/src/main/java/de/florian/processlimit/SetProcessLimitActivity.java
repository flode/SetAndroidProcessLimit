package de.florian.processlimit;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexFile;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SetProcessLimitActivity extends PreferenceActivity {
     @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    private void setupSimplePreferencesScreen() {
        // Add 'general' preferences.
        addPreferencesFromResource(R.xml.pref_general);
        findPreference("number_of_processes").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                ((ListPreference) preference).setValue((String)o);
                try {
                    setProcessLimit(Integer.parseInt(((ListPreference) preference).getValue()));
                } catch (NotPriviledgedException e) {
                    Toast.makeText(SetProcessLimitActivity.this, "App is not installed as priviledged app! Please reread the readme file!", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    public static void setProcessLimit(int limit) throws NotPriviledgedException {
        Log.d("de.florian.processlimit","New limit: " + Integer.toString(limit));
        try {
            Class ActivityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Class IActivityManager = Class.forName("android.app.IActivityManager");

            Method getDefault =  ActivityManagerNative.getMethod("getDefault", (Class[])null);
            Object am = IActivityManager.cast(getDefault.invoke(ActivityManagerNative, (Class[])null));

            Class[] args = new Class[1];
            args[0] = int.class;
            Method setProcessLimit = am.getClass().getMethod("setProcessLimit", args);
            setProcessLimit.invoke(am, limit);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new NotPriviledgedException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new NotPriviledgedException();
        }
    }
}

class NotPriviledgedException extends Exception {

}