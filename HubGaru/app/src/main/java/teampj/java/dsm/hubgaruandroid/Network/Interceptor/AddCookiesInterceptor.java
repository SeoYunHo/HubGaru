package teampj.java.dsm.hubgaruandroid.Network.Interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import teampj.java.dsm.hubgaruandroid.Network.Preference.CookieSharedPreferences;

/**
 * Created by user on 2017-09-15.
 */

public class AddCookiesInterceptor implements Interceptor{

    CookieSharedPreferences cookieSharedPreferences;

    public AddCookiesInterceptor (Context context) {
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

}
