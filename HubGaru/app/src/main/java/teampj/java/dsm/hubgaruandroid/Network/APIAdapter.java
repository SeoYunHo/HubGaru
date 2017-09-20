package teampj.java.dsm.hubgaruandroid.Network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import teampj.java.dsm.hubgaruandroid.Network.Interceptor.AddCookiesInterceptor;
import teampj.java.dsm.hubgaruandroid.Network.Interceptor.ReceivedCookiesInterceptor;

/**
 * Created by user on 2017-09-18.
 */

public class APIAdapter {

    protected static Object retrofit(Context context, Class<?> serviceName) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceName);
    }
}
