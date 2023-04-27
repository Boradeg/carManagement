package com.example.zamppapi

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

//step 2=end point connection
interface ApiEndPoint {

    @GET("carRead.php")
    fun getCarsData(): Call<ApiGetRespon?>?

    @FormUrlEncoded
    @POST("carDelete.php")
    fun deleteCarData(@Field("id") id:Int): Call<ApiDeleteResponse?>?

    @FormUrlEncoded
    @POST("carAdd.php")
    fun addCarData( @Field("name") name:String,
                    @Field("ownName") ownName:String,
                    @Field("number") number:String,
                    @Field("isActive") isActive:String,
                    @Field("fuelType") fuelType:String,
                    @Field("model") model:String,
                    @Field("coverKm") coverKm:String):Call<ApiAddResponse>

    @FormUrlEncoded
    @POST("carUpdate.php")
    fun updateCarData(@Field("id") id: Int,
                      @Field("name") name:String,
                      @Field("ownName") ownName:String,
                      @Field("number") number:String,
                      @Field("isActive") isActive:String,
                      @Field("fuelType") fuelType:String,
                      @Field("model") model:String,
                      @Field("coverKm") coverKm:String):Call<ApiUpdateResponse>

}