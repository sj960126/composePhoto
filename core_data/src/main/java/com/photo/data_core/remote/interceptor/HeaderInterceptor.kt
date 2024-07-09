package com.photo.data_core.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.EOFException
import java.io.IOException

class HeaderInterceptor : Interceptor {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val KEY = "KakaoAK"
        const val REST_API_KEY = "36d7b2c2539dba219c482334ce8fe04d"

        fun Request.Builder.addHeaderDeduplication(name: String, value: String) = this.apply {
            removeHeader(name)
            addHeader(name, value)
        }

    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().run {
            newBuilder().apply {
                addHeaderDeduplication(HEADER_AUTHORIZATION, "$KEY $REST_API_KEY")
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
