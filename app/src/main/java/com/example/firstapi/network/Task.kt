package com.example.firstapi.network

import com.squareup.moshi.Json
import retrofit2.http.Field

data class Task(
    val id: Int,
    @Json(name = "title") val task_title: String,
    @Json(name = "status") val task_status: String
)

data class TaskTitle(
    @Json(name = "title") val task_title: String
)

data class CreatedTaskResponse(
    @Json(name = "msg") val task_msg: String,
    @Json(name = "taskId") val task_id: String
)

data class TaskMsg(
    @Json(name = "msg") val task_msg: String,
)

data class TaskId(
    @field:Json(name = "id") val id: Int
)

data class UpdatedTask(
    @Json(name = "id") val id: Int,
    @Json(name = "status") var task_status: String
)