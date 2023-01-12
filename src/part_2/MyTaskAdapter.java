package part_2;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/***
 * This is an adapter class that extends the Java class "FutureTask" and implements the interface "Comparable".
 * This adapter is needed if because we want to pass a 'Task' type to be used in the priority queue in the thread pool.
 */
public class MyTaskAdapter<T> extends FutureTask<T> implements Comparable<T> {

    private Task<T> task;
    private int prr;

    /***
     * Constructor that takes in a "Callable" object and an integer "prr" as input and initializes the "task" variable
     * with a new "Task" object created from the input callable and "prr" variable.
     * @param callable Object of type Callable.
     * @param prr Of type int. represent the priority.
     */
    public MyTaskAdapter (Callable<T> callable, int prr){
        super(callable);
        this.task = new Task<>(callable);
        this.prr = prr;
    }

    /***
     * Returns the prr member variable.
     * @return The priority.
     */
    public int getPrr() {
        return prr;
    }

    @Override
    public String toString() {
        return "TaskAdapter{" +
                "prr=" + prr +
                '}';
    }

    /***
     * compares this task to the specified object and returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     * @param o the object to be compared.
     * @return An integer.
     */
    @Override
    public int compareTo(@NotNull T o) {
        return ((MyTaskAdapter<T>)o).task.compareTo(this.task);
    }
}
