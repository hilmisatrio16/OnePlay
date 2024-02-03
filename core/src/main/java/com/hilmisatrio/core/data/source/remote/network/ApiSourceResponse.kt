package com.hilmisatrio.core.data.source.remote.network

sealed class ApiSourceResponse<out R> {
    data class Success<out T>(val data: T) : ApiSourceResponse<T>()
    data class Error(val errorMessage: String) : ApiSourceResponse<Nothing>()
    object Empty : ApiSourceResponse<Nothing>()
}