package teampj.java.dsm.hubgaruandroid.Network.Service;

import android.content.Context;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import teampj.java.dsm.hubgaruandroid.Network.APIAdapter;

/**
 * Created by user on 2017-09-22.
 */

public class InfoService extends APIAdapter {

    public static InfoApi getRetrofit(Context context) {
        return (InfoApi) retrofit(context, InfoApi.class);
    }

    public interface InfoApi {
//        개인정보 가져오는 메소드
//        편짐

        @GET("/user/info")
        Call<JSONObject> getInfo();
    }
}
