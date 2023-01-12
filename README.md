# OOP.Assignment2

## Part_1

In this part we are going to creat multiple text files in 3 methods and check which method is the fastest way to count all 
the lines in all the files.

- createTextFiles() - method that creat all the files.
- getNumOfLines() - this method reading and counting the lines in the files one by one. 
  it's the simlest way and probably the slowest.
- getNumOfLinesTreads() - this method reading and counting the lines using separate thread for each file
  This function creates an array of threads, one for each file in the input array of file names. It starts each 
  thread, then waits for all threads to finish using the join() method. Finally, it sums the number of lines counted 
  by each thread and returns the total number of lines.
- getNumOfLinesThreadPool - This code creates a thread pool with a number of threads equal to the number of 
  input files. It then creates a Callable task for each file and submits it to the thread pool using the submit() method. 
  The returned Future object is added to a list. The code then waits for all tasks to complete by iterating through the 
  list of Future objects and calling the get() method on each one. This method blocks until the task associated with the 
  Future has completed and returns the result of the task. The total number of lines is calculated by summing the results 
  of all tasks. Finally, the thread pool is shut down using the shutdown() method.
  
### Conclusion

In the folowing table there is the result of the run time:

| Method                      | Num of files | Num of lines | Time (ms) |
|-----------------------------|--------------|--------------|-----------|
| getNumOfLines               | 3000         | 148300705    | 12809     |
| getNumOfLinesThreads        | 3000         | 148300705    | 4264      |
| getNumOfLinesThreadsPool    | 3000         | 148300705    | 4273      |

We can see that the getNumOfLines() function is the slowest because it's calculat the lines one by one while the thread and 
the thread pool can to it simultaneously.

Threads are useful when you need to create a large number of independent tasks that run concurrently, while thread pools
are useful when you need to create a fixed number of threads to execute a large number of tasks efficiently.

In our case we can see that there is no much differences between thread and thread pool.

### Class diagram
![Screenshot (2)](https://user-images.githubusercontent.com/117936442/212131816-a8550370-e6c2-43f0-86e8-9805b4382577.png)



## Part_2

In this part we create a new type that represents an asynchronous task with priority and a new ThreadPool type that supports
tasks with priority.

There is three classes in this part:
- Task
- CustomExecutor
- MyTaskAdapter

### Task
Task is implements the Callable and Comparable interfaces. The class has two constructors, one
that takes in a Callable object and a TaskType enum, and another that takes in only a Callable object and defaults
the TaskType to OTHER. The class also has a createTask method that returns an instance of the Task class, and overrides 
the call, toString, and compareTo methods. The compareTo method compares two tasks by their TaskType priority value. 
The class also has a method getTaskType which returns the priority value of the task.

### CustomExecutor
CustomExecutor class is extends ThreadPoolExecutor. The class has several constructors and methods. 
The constructor initializes the thread pool size with a minimum and maximum number of threads and a PriorityBlockingQueue 
as the work queue. The class also has a submit method which takes in a Task object, wraps it in a MyTaskAdapter and adds it
to the thread pool, and returns a FutureTask object. There are also submit methods that take in a Callable object and a 
TaskType enum or just a Callable object. The class also has a shutdown method which stops the execution of tasks 
and a gracefullyTerminate method that attempts to stop the execution of tasks gracefully by waiting for the queue to be 
empty and for all running tasks to finish before shutting down the executor. The class also overrides the beforeExecute 
method, which is called before a task is executed, and updates the count of the task in the priorities array. The class 
also has a get_Queue method which returns the work queue, and a getCurrentMax method which returns the current maximum 
priority of the tasks in the queue.

### MyTaskAdapter
MyTaskAdapter class is extends the FutureTask class and implements the Comparable interface. 
The class has a constructor that takes in a Callable object and an integer prr as input and initializes the task variable 
with a new Task object created from the input callable and prr variable. The class also has a getPrr method which returns 
the priority of the task, a toString method which returns a string representation of the object, and a compareTo method 
which compares this task to the specified object. This class is used as a adapter for the task class and allows 
it to be used in the priority queue in the thread pool.

### Class diagram
![Screenshot (3)](https://user-images.githubusercontent.com/117936442/212134825-6e71f42d-86d1-4880-a1e4-f8df2d4ec1db.png)





  
  
