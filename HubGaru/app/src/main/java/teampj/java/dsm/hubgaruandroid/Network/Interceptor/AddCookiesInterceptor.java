package teampj.java.dsm.hubgaruandroid.Network.Interceptor;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
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
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> cookies = (HashSet<String>) cookieSharedPreferences.getHashSet(
                CookieSharedPreferences.COOKIE_SHARED_PREFERENCE_KEY, new HashSet<String>()
        );

        for(String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
            Log.d(cookie.toString(), "cookieCheck");
        }

        return chain.proceed(builder.build());
    }

}
