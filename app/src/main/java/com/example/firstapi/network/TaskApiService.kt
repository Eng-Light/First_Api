package com.example.firstapi.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.seifahmed.com/todolist/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TaskApiService {
    @GET("alltasks")
    suspend fun getTasks(): ApiTask

    @POST("createtask")
    suspend fun createTask(@Body title: TaskTitle): CreatedTaskResponse

    @PUT("updatetask")
    suspend fun updateTask(@Body updatedTask: UpdatedTask): TaskMsg

    @DELETE("deletetask/")
    suspend fun deleteTask(@Query("id") id: Int): TaskMsg
}

object TaskApi {
    val retrofitService: TaskApiService by lazy { retrofit.create(TaskApiService::class.java) }
}