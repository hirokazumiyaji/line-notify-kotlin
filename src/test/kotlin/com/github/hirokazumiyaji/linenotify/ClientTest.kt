package com.github.hirokazumiyaji.linenotify

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import java.util.*

class ClientTest {

    val server: MockWebServer = MockWebServer()

    fun setUp() {
        server.start()
    }

    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun create() {
        val token: String = UUID.randomUUID().toString()
        val client: Client = Client.create(token = token)

        Assert.assertEquals(client.token, token)
    }

    @Test
    fun notifyTest() {
        val response = MockResponse().apply {
            setResponseCode(200)
            setBody("{\"status\":200,\"message\":\"OK\"}")
        }
        server.enqueue(response)

        val client = Client(
                UUID.randomUUID().toString(),
                OkHttpClient.Builder().apply {
                    followRedirects(false)
                }.build(),
                server.url("").toString()
        )

        client.notify(body = NotifyRequest("test message")).subscribe({
            Assert.assertEquals(it.body()?.status, 200)
            Assert.assertEquals(it.body()?.message, "OK")
        }, {
            Assert.fail(it.message)
        })
    }

    @Test
    fun status() {
        val response = MockResponse().apply {
            setResponseCode(200)
            setBody("{\"status\":200,\"message\":\"OK\",\"targetType\":\"USER\",\"target\":\"username\"}")
        }
        server.enqueue(response)

        val client = Client(
                UUID.randomUUID().toString(),
                OkHttpClient.Builder().apply {
                    followRedirects(false)
                }.build(),
                server.url("").toString()
        )

        client.status().subscribe({
            Assert.assertEquals(it.code(), 200)
            Assert.assertEquals(it.body()?.status, 200)
            Assert.assertEquals(it.body()?.message, "OK")
            Assert.assertEquals(it.body()?.targetType, "USER")
            Assert.assertEquals(it.body()?.target, "username")
        }, {
            Assert.fail(it.message)
        })
    }

    @Test
    fun revoke() {
        val response = MockResponse().apply {
            setResponseCode(200)
            setBody("{\"status\":200,\"message\":\"OK\"}")
        }
        server.enqueue(response)

        val client = Client(
                UUID.randomUUID().toString(),
                OkHttpClient.Builder().apply {
                    followRedirects(false)
                }.build(),
                server.url("").toString()
        )

        client.revoke().subscribe({
            Assert.assertEquals(it.code(), 200)
            Assert.assertEquals(it.body()?.status, 200)
            Assert.assertEquals(it.body()?.message, "OK")
        }, {
            Assert.fail(it.message)
        })
    }
}