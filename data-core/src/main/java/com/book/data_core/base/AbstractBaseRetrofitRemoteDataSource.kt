package com.book.data_core.base

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class AbstractBaseRetrofitRemoteDataSource {

    suspend inline fun <T> runWithHandlingResult(crossinline callAction: suspend () -> Response<T>): Result<T?> {
        return try {
            withContext(Dispatchers.IO) {
                val response = callAction()

                withContext(Dispatchers.Main) {
                    when {
                        // statusCode 200..300
                        response.isSuccessful -> {
                            Result.success(response.body())
                        }
                        else -> {
                            Result.failure(exception = Exception(Throwable(message =Gson().fromJson(response.errorBody()?.string(),ErrorResponse::class.java).message)))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Result.failure(e)
            }
        }
    }
}