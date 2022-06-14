package com.example.contactapi.network

import com.example.contactapi.model.Phone
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    companion object{
      val BASE_URL="https://phone-api-8826.herokuapp.com/"
    }
    @GET("/v1/phone")
    suspend fun getPhone():List<Phone>

    @FormUrlEncoded
    @POST("/v1/phone")
    suspend fun setPhone(
        @Field("name") name:String,
        @Field("phoneNo")phoneNo:Long
    ):Phone

    @DELETE("/v1/phone/{userId}")
    suspend fun delete(
        @Path("userId")userId:Int
    ):Response<Unit>

    @FormUrlEncoded
    @PUT("/v1/phone/{userId}")
    suspend fun updatePhone(
        @Path("userId") userId: Int,
        @Field("name") name:String,
        @Field("phoneNo") phoneNo: Long
    ):Response<Unit>
}