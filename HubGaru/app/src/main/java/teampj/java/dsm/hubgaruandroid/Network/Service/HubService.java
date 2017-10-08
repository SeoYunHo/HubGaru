package teampj.java.dsm.hubgaruandroid.Network.Service;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import teampj.java.dsm.hubgaruandroid.Network.APIAdapter;

/**
 * Created by user on 2017-09-22.
 */

public class HubService extends APIAdapter {

    public static HubApi getRetrofit (Context context) {
        return (HubApi) retrofit(context, HubApi.class);
    }

    public interface HubApi {

        @GET("/garu")
        Call<JsonObject> getGaru();

        @GET("/hub")
        Call<JsonObject> getHub();

        @FormUrlEncoded
        @POST("/account/signin")
        Call<Void> singIn(
                @Field("id") String id ,
                @Field("password") String password
        );

        @FormUrlEncoded
        @POST("/account/signup")
        Call<Void> singUp(
                @Field("id") String id,
                @Field("password")String password,
                @Field("name")String name,
                @Field("userIntro") String userIntro,
                @Field("part")String part,
                @Field("phone") String phone
        );

        @GET("/user/info/{id}")
        Call<JsonObject> getInfo(@Path("id") String id);

        @GET("/user/hub/{id}")
        Call<JsonObject> getMyHub(@Path("id") String id);

        @GET("/user/garu/{id}")
        Call<JsonObject> getMyGaru(@Path("id") String id);

        @GET("/hub/good/{hubId}")
        Call<JsonObject> getLike(@Path("hubId") String hubId);

        @POST("/hub/good/{hubId}")
        Call<Void> plus(@Path("hubId") String hubId);

        @DELETE("/hub/good/{hubId}")
        Call<Void> minus(@Path("hubId") String hubId);
//==================================================================complete========================================================================
        @FormUrlEncoded
<<<<<<< HEAD
=======
        @Multipart
>>>>>>> d8f840061d34a7bde3e28fa14543551e03e606ce
        @POST("/hub/comment/{hubId}")
        Call<Void> addComment(@Path("hubId") String hubId,
                              @Field("comment") String comment,
                              @Field("id") String id,
                              @Field("date") String date);

        @GET("/hub/comment/{hubId}")
        Call<JsonObject> getComments(@Path("hubId") String hubId);

        @GET("/upload/{filename}")
        Call<JsonObject> getPic(@Path("filename") String filename);

         @GET("/upload/{filename}")
         Call<JsonObject> getSong(@Path("fielname") String filename);

        @POST("/garu/{id}")
        Call<JsonObject> makeGaru(@Path("id") String id, String name, String intro, String file, String img);

        @POST("/hub/id/{garuId}")
        Call<Void> makeHub(@Path("garuId") String garuId, String name, String file, String img);
    }
}
