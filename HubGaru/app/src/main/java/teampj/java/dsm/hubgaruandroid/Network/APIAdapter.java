package teampj.java.dsm.hubgaruandroid.Network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import teampj.java.dsm.hubgaruandroid.Network.Interceptor.AddCookiesInterceptor;
import teampj.java.dsm.hubgaruandroid.Network.Interceptor.ReceivedCookiesInterceptor;

/**
 * Created by user on 2017-09-18.
 */

public class APIAdapter {

//    private static Retrofit retrofit;
//    private static Context context;

    protected static Object retrofit(Context context, Class<?> serviceName) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(client)
                .build();

        return retrofit.create(serviceName);
    }

//    public static Retrofit getRetrofit() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new ReceivedCookiesInterceptor(context))
//                .addNetworkInterceptor(new AddCookiesInterceptor(context))
//                .build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl("")
//                .client(client)
//                .build();
//
//        return retrofit;
//    }
}
