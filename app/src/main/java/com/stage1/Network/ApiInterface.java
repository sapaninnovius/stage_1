package com.stage1.Network;

import com.google.gson.JsonObject;
import com.stage1.Models.GetAllMediaDetail;
import com.stage1.Models.ResponseLogin;
import com.stage1.Models.ResponseRegistration;
import com.stage1.Models.ResponseUpdateProfile;
import com.stage1.ResponseRole;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    /*@GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/

    @GET("getrole")
    Call<ResponseRole> getRole();
    @Headers({
            "Accept: application/json",
            "User-Agent: multipart/form-data"
    })
    @POST("registration")
    Call<ResponseRegistration> newRegister(
            @Query("name") String name,
            @Query("contact_number") String phone,
            @Query("role_id") int post,
            @Query("email") String email,
            @Query("password") String password,
            @Query("device_id") String device_id,
            @Query("street") String street,
            @Query("apt") String apt,
            @Query("city") String city,
            @Query("zipcode") String zipcode,
            @Query("state") String state);


    @POST("login")
    Call<ResponseLogin> login(
            @Query("email") String email,
            @Query("password") String password,
            @Query("device_id") String deviceID,
            @Query("lat") String lat,
            @Query("long)") String lng);

    @Multipart
    @POST("updateprofile")
    Call<JsonObject> updateProfile(@Query("name")String name,
                                   @Query("user_id")String user_id,
                                   @Query("contact_number")String contact_number,
                                   @Query("device_id")String device_id,
                                   @Query("role_id")String role_id,
                                   @Query("bar_id")String bar_id,
                                   @Query("street")String street,
                                   @Query("apt")String apt,
                                   @Query("city")String city,
                                   @Query("zipcode")String zipcode,
                                   @Query("state")String state,
                                   @Part MultipartBody.Part profile_pic);


    @POST("getallmediadetail")
    Call<GetAllMediaDetail> getallmediadetail(@Query("user_id")String user_id);
}
