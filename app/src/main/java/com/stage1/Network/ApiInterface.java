package com.stage1.Network;

import com.stage1.Models.ResponseLogin;
import com.stage1.Models.ResponseRegistration;
import com.stage1.ResponseRole;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    /*@GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/

    @GET("getrole")
    Call<ResponseRole> getRole();

    @POST("registration")
    Call<ResponseRegistration> newRegister(
            @Query("name") String name,
            @Query("contact_number") String phone,
            @Query("role_id") int post,
            @Query("email") String email,
            @Query("password") String password);


    @POST("login")
    Call<ResponseLogin> login(
            @Query("email") String email,
            @Query("password") String password,
            @Query("device_id") String deviceID,
            @Query("lat") String lat,
            @Query("long)") String lng);
}