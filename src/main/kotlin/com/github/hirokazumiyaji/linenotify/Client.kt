package com.github.hirokazumiyaji.linenotify

import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

class Client(var token: String = "", httpClient: OkHttpClient, endpoint: String = "https://notify-api.line.me") {

    private val client: Retrofit = createClient(endpoint, httpClient)

    companion object Factory {
        fun create(token: String = "", httpClient: OkHttpClient? = null): Client {
            val client: OkHttpClient = httpClient ?: OkHttpClient.Builder().followRedirects(false).build()
            return Client(token, client)
        }
    }

    private fun createClient(endpoint: String, httpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        client(httpClient)
        baseUrl(endpoint)
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    fun notify(token: String = "", body: NotifyRequest, file: File? = null): Single<Response<NotifyResponse>> {
        val accessToken = if (token.isNullOrEmpty()) this.token else token
        if (file == null) {
            return createRequest().notify(accessToken, body.toFormData())
        } else {
            val fileBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
            return createRequest().notify(accessToken, body.toFormData(), fileBody)
        }

    }

    fun status(token: String = ""): Single<Response<StatusResponse>> {
        val accessToken = if (token.isNullOrEmpty()) this.token else token
        return createRequest().status(accessToken)
    }

    fun revoke(token: String = ""): Single<Response<StatusResponse>> {
        val accessToken = if (token.isNullOrEmpty()) this.token else token
        return createRequest().revoke(accessToken)
    }

    private fun createRequest(): Repository = client.create(Repository::class.java)

}
