package com.example.firstapi.alltasks

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapi.R
import com.example.firstapi.network.Task

/*The @BindingAdapter annotation tells data binding to execute this binding adapter
when a View item has the imageUrl attribute.*/
@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Task>?
) {
    val adapter = recyclerView.adapter as TasksAdapter
    adapter.submitList(data)
}

@BindingAdapter("taskTitle")
fun bindTextView(
    textView: TextView,
    tv_task: String?
) {
    textView.text = tv_task
}

@BindingAdapter("TasksApiStatus")
fun bindStatus(
    statusImageView: ImageView,
    status: TasksApiStatus?
) {
    when (status) {
        TasksApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        TasksApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        TasksApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}