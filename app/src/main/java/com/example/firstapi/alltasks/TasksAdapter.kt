package com.example.firstapi.alltasks

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapi.databinding.ItemViewBinding
import com.example.firstapi.network.Task
import com.example.firstapi.network.UpdatedTask

class TasksAdapter : ListAdapter<Task,
        TasksAdapter.TasksViewHolder>(DiffCallback) {
    var onBtItemClick: ((Int) -> Unit)? = null
    var onRdItemClick: ((UpdatedTask) -> Unit)? = null
    //var isChecked:Boolean? = null

    inner class TasksViewHolder(
        private var binding: ItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btTask.setOnClickListener{
                onBtItemClick?.invoke(binding.task!!.id)
            }
            binding.rdButton.setOnClickListener {
                onRdItemClick?.invoke(UpdatedTask(binding.task!!.id,binding.task!!.task_status))
            }
        }

        fun bind(Task: Task) {
            binding.task = Task
            Log.d("Reload_Item", Task.task_status)
            Log.d("Reload_Item", Task.task_title)
            if (Task.task_status == "active") {
                binding.tvTask.paintFlags = binding.tvTask.paintFlags and  Paint.STRIKE_THRU_TEXT_FLAG
                binding.rdButton.isChecked = false
                Log.d("Reload_Item","No")
            } else if (Task.task_status == "expired"){
                binding.tvTask.paintFlags = /*binding.tvTask.paintFlags or*/ Paint.STRIKE_THRU_TEXT_FLAG
                binding.rdButton.isChecked = true
                Log.d("Reload_Item","Yes")
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task_status == newItem.task_status
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    /*private fun taskDone(position: Int) {
        TODO()
    }*/
}