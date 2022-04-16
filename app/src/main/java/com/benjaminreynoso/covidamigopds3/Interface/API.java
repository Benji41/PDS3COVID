package com.benjaminreynoso.covidamigopds3.Interface;

//import com.example.covidamigopds3.Model.User;

import com.benjaminreynoso.covidamigopds3.Model.Event;
import com.benjaminreynoso.covidamigopds3.Model.Medida;
import com.benjaminreynoso.covidamigopds3.Model.User;

import java.util.List;
import okhttp3.Callback;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/registrarusuario")
    Call<User> insertUser(@Field("id") String email, @Field("name") String first, @Field("surNames") String last, @Field("age") Integer age);

    @GET("/eventos")
    Call<List<Event>> getEventos(@Query("id") String id);

    @GET("/eventomedida")
    Call<List<Medida>> getMedidasEventos(@Query("id_evento") int id);

    @GET("/eventohost")
    Call<User> getHostEventos(@Query("id_evento") int id);


}