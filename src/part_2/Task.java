package part_2;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public class Task<T> implements Comparable<Task<T>>, Callable<T> {
    private Callable<T> task;
    private TaskType taskType;

    /***
     * Constructor for Task with two arguments
     * @param task of type Callable.
     * @param taskType of type TaskType.
     */
    public Task(Callable<T> task, TaskType taskType) {
        this.task = task;
        this.taskType = taskType;
    }

    /***
     * Constructor for Task with one argument, call the first constructor with 'TaskType.OTHER' as TaskType.
     * @param task Object of type Callable.
     */
    public Task(Callable<T> task){
        this(task, TaskType.OTHER);
    }

    /***
     * Create an instance of the "Task" class by calling the first constructor.
     * @param callable of type Callable.
     * @param taskType of type TaskType.
     * @return an instance of the "Task" class.
     */
    public static Task<?> createTask(Callable<?> callable, TaskType taskType) {
        return new Task<>(callable,taskType);
    }

    /***
     * Overriding the call() method of the Callable<T> interface.
     * @return A value of type T.
     * @throws Exception Possible exception thrown by the call() method at the return statment.
     */
    @Override
    public T call() throws Exception {
        return task.call();
    }

    /***
     *
     * @return The object as a string.
     */
    @Override
    public String toString() {
        return "Task{" +
                "callable=" + task +
                ", taskType=" + taskType +
                '}';
    }

    /***
     * Compare two tasks by their value.
     * @param o the object to be compared.
     * @return a negative number, zero, or a positive number depending on whether the current object is less than, equal to, or
     * greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull Task<T> o) {
        return Integer.compare(this.taskType.getPriorityValue(), o.taskType.getPriorityValue());
    }

    /***
     * Return The value of the priority
     * @return The value of the priority
     */
    public int getTaskType() {
        return taskType.getPriorityValue();
    }
}