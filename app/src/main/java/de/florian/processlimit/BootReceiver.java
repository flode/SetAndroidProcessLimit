package de.florian.processlimit;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.util.Log;

import static de.florian.processlimit.SetProcessLimitActivity.setProcessLimit;

public class BootReceiver extends BroadcastReceiver {

    public static final String TAG = "BootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if( "android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            setProcessLimit(Integer.parseInt(prefs.getString("number_of_processes", "-1")));
        } else {
            Log.e(TAG, "Received unexpected intent " + intent.toString());
        }
    }
}