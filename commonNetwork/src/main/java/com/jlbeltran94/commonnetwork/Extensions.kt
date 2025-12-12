package com.jlbeltran94.commonnetwork

import com.jlbeltran94.commonmodel.exception.DomainException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.success(apiCall())
    } catch (e: HttpException) {
        val errorMessage = e.response()?.errorBody()?.string()
        Result.failure(DomainException.ApiError(e.code(), errorMessage, e))
    } catch (e: IOException) {
        Result.failure(DomainException.IOError(e))
    } catch (e: Exception) {
        Result.failure(DomainException.UnknownError(e))
    }
}