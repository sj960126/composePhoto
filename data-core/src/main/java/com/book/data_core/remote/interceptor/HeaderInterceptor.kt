package com.book.data_core.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.EOFException
import java.io.IOException

class HeaderInterceptor : Interceptor {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"


        fun Request.Builder.addHeaderDeduplication(name: String, value: String) = this.apply {
            removeHeader(name)
            addHeader(name, value)
        }

        fun bodyToString(request: Request): String? {
            return try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body?.writeTo(buffer).toString()
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            } catch (e: NullPointerException) {
                "did not have body"
            }
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().run {
            newBuilder().apply {

                addHeaderDeduplication(HEADER_AUTHORIZATION, "apiKey")
                method(method, body)

            }.let { builder ->
                return try {
                    chain.proceed(builder.build())
                } catch (e: EOFException) {
                    chain.proceed(builder.build())
                }
            }
        }
    }
}
