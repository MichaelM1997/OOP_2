package part_2;

import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor{

    private int max = 10;
    int[] priorities;
    private static int maxProcessors = Runtime.getRuntime().availableProcessors() - 1;
    private static int minProcessors = Runtime.getRuntime().availableProcessors() / 2;

    /***
     * Constructor for CustomExecutor class.
     */
    public CustomExecutor() {
        super(minProcessors, maxProcessors, 300, TimeUnit.MILLISECONDS,  new PriorityBlockingQueue<>());
        priorities = new int[max];
    }

    /***
     * Creates a new instance of the TaskAdapter<T> class, passing the task and the value returned by the getTaskType() method of the
     * task object as an argument.
     * The execute causes the taskAdapter object to be added to the thread pool, where it will be executed by one of the threads.
     * @param task Object of type Task.
     * @return Object of type FutureTask<T>.
     */
    public <T> FutureTask<T> submit(Task<T> task) {
        priorities[task.getTaskType() - 1] += 1;
        MyTaskAdapter<T> taskAdapter = new MyTaskAdapter<>(task, task.getTaskType());
        if (this.max > task.getTaskType()){
            this.max = task.getTaskType();
        }
        super.execute(taskAdapter);
        return taskAdapter;
    }

    /***
     * This method is similar to the previous one, but it accepts a single argument of type Callable<T>, instead of Task<T>.
     * @param callable Object of type Callable.
     * @return Call to the first submit method.
     */
    public <T> FutureTask<T> submit(Callable<T> callable) {
        return submit(new Task<>(callable));
    }

    /***
     * This method is similar to the first submit method, but it accepts two arguments: a Callable<T> object and a TaskType object.
     * @param callable Object of type Callable.
     * @param taskType Object of type TaskType.
     * @return Call to the first submit method.
     */
    public <T> FutureTask<T> submit(Callable<T> callable, TaskType taskType) {
        return submit(new Task<>(callable, taskType));
    }

    /***
     * Calls the shutdown() method of the executor field, which is an instance of the ThreadPoolExecutor class.
     */
    public void shutdown() {
        super.shutdown();
    }

    /***
     * attempt to terminate the executor gracefully by waiting for the queue to be empty and for all running tasks to finish
     * before shutting down the executor
     */
    public void gracefullyTerminate() {
        super.shutdown();
        try {
            super.awaitTermination(300,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        MyTaskAdapter r1 = (MyTaskAdapter) r;
        this.priorities[r1.getPrr()-1] -= 1;
    }

    public BlockingQueue<Runnable> get_Queue() {
        return super.getQueue();
    }

    /***
     * return the current max as an int.
     * @return
     */
    public int getCurrentMax() {
        for (int i = 0; i < 10; i++) {
            if (priorities[i] != 0){
                return i+1;
            }
        }
        return 0;
    }
}