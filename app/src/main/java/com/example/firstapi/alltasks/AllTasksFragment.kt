package com.example.firstapi.alltasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.firstapi.databinding.FragmentAllTasksBinding
import com.google.android.material.snackbar.Snackbar

class AllTasksFragment : Fragment() {

    private val viewModel: AllTasksViewModel by viewModels()
    private lateinit var binding: FragmentAllTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTasksBinding.inflate(inflater)

        binding.lifecycleOwner = this

        // Giving the binding access to the AllTasksViewModel
        binding.viewModel = viewModel

        binding.rvTasks.adapter = TasksAdapter()
        deleteTask()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Confirm Add ?", Snackbar.LENGTH_SHORT)
                .setAction("ADD") { addTask() }.show()
        }

        refreshApp()

        return binding.root
    }

    private fun addTask() {
        val newTask: String = binding.textInputEditText.text.toString()
        viewModel.createTask(newTask)
        updateRv()
    }

    private fun updateRv() {
        val taskList = viewModel.tasks.value?.task_list
        bindRecyclerView(binding.rvTasks, taskList)
    }

    private fun deleteTask() {
        val adapter = binding.rvTasks.adapter as TasksAdapter
        adapter.submitList(viewModel.tasks.value?.task_list)
        adapter.onBtItemClick = {
            viewModel.deleteTask(it)
        }
        adapter.onRdItemClick = {
            viewModel.updateTask(it)
            /*if (viewModel.updatedStatus.value == UpdatedStatus.DONE){
                viewModel.
            }*/
        }
        Log.d("deleteTask","Done")
    }

    private fun refreshApp(){
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.refreshApp()
            binding.swipeToRefresh.isRefreshing = false
        }
    }
}