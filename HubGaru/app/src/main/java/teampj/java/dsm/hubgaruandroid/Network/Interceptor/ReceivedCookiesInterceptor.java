package teampj.java.dsm.hubgaruandroid.Network.Interceptor;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;
import teampj.java.dsm.hubgaruandroid.Network.Preference.CookieSharedPreferences;

/**
 * Created by user on 2017-09-15.
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    private CookieSharedPreferences cookieSharedPreferences;

    public ReceivedCookiesInterceptor (Context context) {
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if(!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for(String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }

            cookieSharedPreferences.putHashSet(CookieSharedPreferences.COOKIE_SHARED_PREFERENCE_KEY, cookies);

        }
        return response;
    }

}
