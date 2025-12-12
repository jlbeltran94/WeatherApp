package com.jlbeltran94.commonmodel.exception

import java.io.IOException

/**
 * A sealed class representing custom domain exceptions.
 */
sealed class DomainException(cause: Throwable) : Exception(cause) {

    /**
     * Represents a network-related error (e.g., no internet connection).
     */
    class IOError(cause: IOException) : DomainException(cause)

    /**
     * Represents an error from the API (e.g., 4xx or 5xx response).
     * @param code The HTTP status code.
     * @param errorMessage The error message from the server, if available.
     */
    data class ApiError(
        val code: Int,
        val errorMessage: String?,
        override val cause: Throwable
    ) : DomainException(cause)

    /**
     * Represents an unknown or unexpected error.
     */
    class UnknownError(cause: Throwable) : DomainException(cause)
}
