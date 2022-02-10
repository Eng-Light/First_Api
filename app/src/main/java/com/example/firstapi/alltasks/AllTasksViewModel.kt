package com.example.firstapi.alltasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapi.network.*
import kotlinx.coroutines.launch

enum class TasksApiStatus { LOADING, ERROR, DONE }
enum class CreateStatus { LOADING, ERROR, DONE }
enum class DeletedStatus { LOADING, ERROR, DONE }
enum class UpdatedStatus { LOADING, ERROR, DONE }
enum class IsCheckedStatus { TRUE,FALSE }

/**
 * The [ViewModel] that is attached to the [AllTasksFragment].
 */
class AllTasksViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<TasksApiStatus>()
    private val _createStatus = MutableLiveData<CreateStatus>()
    private val _deletedStatus = MutableLiveData<DeletedStatus>()
    private val _updatedStatus = MutableLiveData<UpdatedStatus>()
    private val _isCheckedStatus = MutableLiveData<IsCheckedStatus>()
    private val _tasks = MutableLiveData<ApiTask>()

    // The external immutable LiveData for the request status
    val status: LiveData<TasksApiStatus> = _status
    val createStatus: LiveData<CreateStatus> = _createStatus
    val deletedStatus: LiveData<DeletedStatus> = _deletedStatus
    val updatedStatus: LiveData<UpdatedStatus> = _updatedStatus
    val isCheckedStatus: LiveData<IsCheckedStatus> = _isCheckedStatus
    val tasks: LiveData<ApiTask> = _tasks

    /**
     * Call getTasks() on init so we can display status immediately.
     */

    init {
        getTasks()
    }

    /**
     * Gets Tasks information from the Tasks API Retrofit service and updates the
     * [Task] [LiveData].
     */
    private fun getTasks() {
        viewModelScope.launch {
            _status.value = TasksApiStatus.LOADING
            try {
                val result = TaskApi.retrofitService.getTasks()
                _tasks.value = result
                Log.d("ViewModel_getTasks", _tasks.value!!.task_list[0].task_title)
                _status.value = TasksApiStatus.DONE
            } catch (e: Exception) {
                _status.value = TasksApiStatus.ERROR
                _tasks.value = ApiTask("", listOf())
            }
        }
    }

    fun createTask(newTask: String) {
        _createStatus.value = CreateStatus.LOADING
        viewModelScope.launch {
            try {
                val newTitle = TaskTitle(newTask)
                val response = TaskApi.retrofitService.createTask(newTitle)
                _createStatus.value = CreateStatus.DONE
                getTasks()
            } catch (e: Exception) {
                Log.d("ViewModel_createTask", "Response Field")
                _createStatus.value = CreateStatus.ERROR
            }
        }
    }

    fun deleteTask(deletedTask: Int) {
        _deletedStatus.value = DeletedStatus.LOADING
        viewModelScope.launch {
            try {
                val id = TaskId(deletedTask)
                Log.d("ViewModel_deleteTask", id.toString())
                val response = TaskApi.retrofitService.deleteTask(deletedTask)
                _deletedStatus.value = DeletedStatus.DONE
                getTasks()
                //Log.d("")
            } catch (e: Exception) {
                Log.d("ViewModel_deleteTask", e.toString())
                _deletedStatus.value = DeletedStatus.ERROR
            }
        }
    }

    fun updateTask(updatedTask: UpdatedTask){
        _updatedStatus.value = UpdatedStatus.LOADING
        val updated = updateHelper(updatedTask)
        viewModelScope.launch {
            try {
                val task = UpdatedTask(updated.id,updated.task_status)
                Log.d("ViewModel_updateTask", task.toString())
                val response = TaskApi.retrofitService.updateTask(task)
                _updatedStatus.value = UpdatedStatus.DONE
                getTasks()
                //Log.d("")
            } catch (e: Exception) {
                Log.d("ViewModel_updateTask", e.toString())
                _updatedStatus.value = UpdatedStatus.ERROR
            }
        }
    }

    private fun updateHelper(updatedTask: UpdatedTask): UpdatedTask {
        if (updatedTask.task_status == "active"){
            updatedTask.task_status = "expired"
            //_isCheckedStatus.value = IsCheckedStatus.FALSE
        }else if(updatedTask.task_status == "expired"){
            updatedTask.task_status = "active"
            //_isCheckedStatus.value = IsCheckedStatus.TRUE
        }
        return updatedTask
    }

    fun refreshApp(){
        getTasks()
    }
}