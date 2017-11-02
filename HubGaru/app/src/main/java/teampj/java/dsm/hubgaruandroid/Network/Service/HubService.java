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

        @GET("/hub/rank/good")
        Call<JsonObject> getTopHub();

        @GET("/hub/rank/date")
        Call<JsonObject> getNewHub();

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

        @GET("/garu")
        Call<JsonObject> getGaru();

        @GET("/hub/good/{hubId}")
        Call<JsonObject> getLike(@Path("hubId") String hubId);

        @POST("/hub/good/{hubId}")
        Call<Void> plus(@Path("hubId") String hubId);

        @DELETE("/hub/good/{hubId}")
        Call<Void> minus(@Path("hubId") String hubId);

        @FormUrlEncoded
        @POST("/hub/comment/{hubId}")
        Call<Void> addComment(@Path("hubId") String hubId,
                              @Field("comment") String comment,
                              @Field("id") String id,
                              @Field("date") String date);

        @GET("/hub/comment/{hubId}")
        Call<JsonObject> getComments(@Path("hubId") String hubId);

        @FormUrlEncoded
        @POST("/garu/{id}")
        Call<Void> makeGaru(@Path("id") String id,
                                  @Field("name") String name,
                                  @Field("intro") String intro,
                                  @Field("file") String file,
                                  @Field("img") String img);

        @FormUrlEncoded
        @POST("/hub/{garuId}")
        Call<Void> makeHub(@Path("garuId") String garuId,
                           @Field("name") String name,
                           @Field("file") String file,
                           @Field("img") String img,
                           @Field("date") String date);

        @FormUrlEncoded
        @POST("/account/modify/password/{id}")
        Call<Void> changePw(@Path("id") String id,
                            @Field("password") String password);

        @FormUrlEncoded
        @POST("/file/{filename}")
        Call<Void> uploadFIle(@Path("filename") String fileURL);
    }
}
