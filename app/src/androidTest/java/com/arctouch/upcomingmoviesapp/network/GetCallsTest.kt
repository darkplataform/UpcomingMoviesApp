package com.arctouch.upcomingmoviesapp.network


import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.arctouch.upcomingmoviesapp.movies.MoviesActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) class GetCallsTest {

    @Rule @JvmField var activityTestRule = ActivityTestRule(MoviesActivity::class.java)

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun initDb() {
    }

    @After fun closeDb(){
    }

    @Test fun insertTaskAndGetById() {
        TMdb.getLastMovies("2019-01-01",1).observe(activityTestRule.activity, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("MoviesActivity", "--> Loading...")

                }
                Resource.SUCCESS -> {
                    Log.d("MoviesActivity", "--> Success! | loaded ${it.data?.size ?: 0} records.")
                    Log.d("MoviesActivity", "--> Success! | ${it.data}")
                    Assert.assertEquals(20,it.data?.size ?: 0)
                }
                Resource.ERROR -> {
                    Log.d("MoviesActivity", "--> Error!")
                    Assert.fail()
                }
            }
        })

    }

}



//
//    @Test fun insertTaskReplacesOnConflict() {
//        // Given that a task is inserted
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When a task with the same id is inserted
//        val newTask = Task(NEW_TITLE, NEW_DESCRIPTION, DEFAULT_ID).apply {
//            isCompleted = NEW_IS_COMPLETED
//        }
//        database.taskDao().insertTask(newTask)
//
//        // When getting the task by id from the database
//        val loaded = database.taskDao().getTaskById(DEFAULT_TASK.id)
//
//        // The loaded data contains the expected values
//        assertTask(loaded, DEFAULT_ID, NEW_TITLE, NEW_DESCRIPTION, NEW_IS_COMPLETED)
//    }
//
//    @Test fun insertTaskAndGetTasks() {
//        // When inserting a task
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When getting the tasks from the database
//        val tasks = database.taskDao().getTasks()
//
//        // There is only 1 task in the database
//        assertThat(tasks.size, `is`(1))
//        // The loaded data contains the expected values
//        assertTask(tasks[0], DEFAULT_ID, DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_IS_COMPLETED)
//    }
//
//    @Test fun updateTaskAndGetById() {
//        // When inserting a task
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When the task is updated
//        val updatedTask = Task(NEW_TITLE, NEW_DESCRIPTION, DEFAULT_ID).apply {
//            isCompleted = NEW_IS_COMPLETED
//        }
//        database.taskDao().updateTask(updatedTask)
//
//        // When getting the task by id from the database
//        val loaded = database.taskDao().getTaskById(DEFAULT_ID)
//
//        // The loaded data contains the expected values
//        assertTask(loaded, DEFAULT_ID, NEW_TITLE, NEW_DESCRIPTION, NEW_IS_COMPLETED)
//    }
//
//    @Test fun updateCompletedAndGetById() {
//        // When inserting a task
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When the task is updated
//        database.taskDao().updateCompleted(DEFAULT_TASK.id, false)
//
//        // When getting the task by id from the database
//        val loaded = database.taskDao().getTaskById(DEFAULT_ID)
//
//        // The loaded data contains the expected values
//        assertTask(loaded, DEFAULT_TASK.id, DEFAULT_TASK.title, DEFAULT_TASK.description, false)
//    }
//
//    @Test fun deleteTaskByIdAndGettingTasks() {
//        // Given a task inserted
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When deleting a task by id
//        database.taskDao().deleteTaskById(DEFAULT_TASK.id)
//
//        // When getting the tasks
//        val tasks = database.taskDao().getTasks()
//
//        // The list is empty
//        assertThat(tasks.size, `is`(0))
//    }
//
//    @Test fun deleteTasksAndGettingTasks() {
//        // Given a task inserted
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When deleting all tasks
//        database.taskDao().deleteTasks()
//
//        // When getting the tasks
//        val tasks = database.taskDao().getTasks()
//
//        // The list is empty
//        assertThat(tasks.size, `is`(0))
//    }
//
//    @Test fun deleteCompletedTasksAndGettingTasks() {
//        // Given a completed task inserted
//        database.taskDao().insertTask(DEFAULT_TASK)
//
//        // When deleting completed tasks
//        database.taskDao().deleteCompletedTasks()
//
//        // When getting the tasks
//        val tasks = database.taskDao().getTasks()
//
//        // The list is empty
//        assertThat(tasks.size, `is`(0))
//    }
//
//    private fun assertTask(
//        task: Task?,
//        id: String,
//        title: String,
//        description: String,
//        completed: Boolean
//    ) {
//        assertThat<Task>(task as Task, notNullValue())
//        assertThat(task.id, `is`(id))
//        assertThat(task.title, `is`(title))
//        assertThat(task.description, `is`(description))
//        assertThat(task.isCompleted, `is`(completed))
//    }
//
//    companion object {
//
//        private val DEFAULT_TITLE = "title"
//        private val DEFAULT_DESCRIPTION = "description"
//        private val DEFAULT_ID = "id"
//        private val DEFAULT_IS_COMPLETED = true
//        private val DEFAULT_TASK = Task(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_ID).apply {
//            isCompleted = DEFAULT_IS_COMPLETED
//        }
//
//        private val NEW_TITLE = "title2"
//        private val NEW_DESCRIPTION = "description2"
//        private val NEW_IS_COMPLETED = true
//    }
