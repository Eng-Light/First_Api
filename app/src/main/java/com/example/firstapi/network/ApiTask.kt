package com.example.firstapi.network

import com.squareup.moshi.Json

data class ApiTask(
    @Json(name = "msg") val task_msg: String,
    @Json(name = "tasks") val task_list: List<Task>
)