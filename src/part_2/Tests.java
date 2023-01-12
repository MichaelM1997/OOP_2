package part_2;

import org.junit.Test;
import java.util.concurrent.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };

        var priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()-> String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " + customExecutor.getCurrentMax());

        customExecutor.gracefullyTerminate();
    }

    @Test
    public void test(){
        CustomExecutor customExecutor = new CustomExecutor();

        Callable<String> callable_1 = ()-> {
            Thread.sleep(1000);
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };

        Callable<Integer> callable_2 = ()-> {
            Thread.sleep(1000);
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        };

        Future<?>[] stringFuture = new Future[20];
        for (int i = 0; i < stringFuture.length; i++) {
            if (i <= stringFuture.length/2)
                stringFuture[i] = customExecutor.submit(callable_1, TaskType.OTHER);
            else
                stringFuture[i] = customExecutor.submit(callable_2, TaskType.COMPUTATIONAL);
        }

        Object[] array = customExecutor.getQueue().toArray();
        for (int i = 0; i < customExecutor.getQueue().size(); i++) {
            int finalI = i;
            Object[] finalArray1 = array;
            logger.info(()-> "The "+ finalI +"t'h element has priority of "+((MyTaskAdapter<?>) finalArray1[finalI]).getPrr());
        }
        logger.info(()-> "Current max priority = " + customExecutor.getCurrentMax());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ignored) {
        }
        array = customExecutor.getQueue().toArray();
        for (int i = 0; i < customExecutor.getQueue().size(); i++) {
            int finalI = i;
            Object[] finalArray = array;
            logger.info(()-> "The "+ finalI +"t'h element after sleep, has priority of "+((MyTaskAdapter<?>) finalArray[finalI]).getPrr());
        }
        logger.info(()-> "Current max priority = " + customExecutor.getCurrentMax());

        customExecutor.gracefullyTerminate();
    }


}


