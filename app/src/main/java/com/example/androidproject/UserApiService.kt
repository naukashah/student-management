package com.example.androidproject

import retrofit2.Call
import retrofit2.http.*

interface UserApiService {
    @GET("all")
    fun getAll() : Call<List<User>>

    @POST("{name}/{term}/{university}")
    fun set(@Path("name") name: String, @Path("term") term:
    String, @Path("university") university: String): Call<Void>

    @PUT("update/{id}/{name}/{term}/{university}")
    fun update(@Path("id") id: String, @Path("name") name: String, @Path("term") term:
    String, @Path("university") university: String): Call<Void>

    @DELETE("delete/{id}")
    fun delete(@Path("id") id: String): Call<Void>
}
