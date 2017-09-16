package teampj.java.dsm.hubgaruandroid.Network.Preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

/**
 * Created by user on 2017-09-15.
 */

public class CookieSharedPreferences {

    private Context context;
    public static final String COOKIE_SHARED_PREFERENCE_KEY = "hubgaru.cookie";
    private static CookieSharedPreferences cookieSharedPreferences = null;
    private SharedPreferences sharedPreferences;

    public static CookieSharedPreferences getInstanceOf(Context c) {
        if(cookieSharedPreferences == null) {
            cookieSharedPreferences = new CookieSharedPreferences(c);
        }
        return cookieSharedPreferences;
    }

    /**
    * @param context
     */

    public CookieSharedPreferences(Context context) {
        this.context = context;
        final String COOKIE_SHARED_PREFERENCE_NAME = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(COOKIE_SHARED_PREFERENCE_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * @param key
     * @param hashSet
     */

    public void putHashSet(String key, HashSet<String> hashSet) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, hashSet);
        editor.commit();
    }

    /**
     * @param key
     * @param cookie
     * @return
     */

    public HashSet<String> getHashSet (String key, HashSet<String> cookie) {
        try {
            return (HashSet<String>) sharedPreferences.getStringSet(key, cookie);
        } catch (Exception e) {
            return cookie;
        }
    }

}
