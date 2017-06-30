package com.github.hirokazumiyaji.linenotify

import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Repository {

    @FormUrlEncoded
    @POST("/api/notify")
    fun notify(@Header("Authorization") token: String,
               @FieldMap body: Map<String, String>): Single<Response<NotifyResponse>>

    @Multipart
    @POST("/api/notify")
    fun notify(@Header("Authorization") token: String,
               @PartMap body: Map<String, String>,
               @Part("imageFile") file: RequestBody): Single<Response<NotifyResponse>>

    @GET("/api/status")
    fun status(@Header("Authorization") token: String): Single<Response<StatusResponse>>

    @POST("/api/revoke")
    fun revoke(@Header("Authorization") token: String): Single<Response<StatusResponse>>

}
