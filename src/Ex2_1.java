import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] files = Ex2_1.createTextFiles(5000, 2, 100000);
        long start_time, end_time;

        start_time = System.currentTimeMillis();
        System.out.println("Num of lines: " + getNumOfLines(files));
        end_time = System.currentTimeMillis();
        System.out.println("Time for getNumOfLines: "+ (end_time - start_time) + " ms");

        start_time = System.currentTimeMillis();
        System.out.println("Num of lines: " + getNumOfLinesThreads(files));
        end_time = System.currentTimeMillis();
        System.out.println("Time for getNumOfLinesThreads: " + (end_time - start_time) + " ms");

        start_time = System.currentTimeMillis();
        System.out.println("Num of lines: " + getNumOfLinesThreadPool(files));
        end_time = System.currentTimeMillis();
        System.out.println("Time for getNumOfLinesThreadsPool: " + (end_time - start_time) + " ms");

    }

    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] fileNames = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(bound);
            try {
                String f_name = "file_num_" + (i + 1) + ".txt";
                fileNames[i] = f_name;
                FileWriter fileWriter = new FileWriter(fileNames[i]);
                for (int j = 0; j <= x; j++) {
                    fileWriter.write("line number " + (j + 1) + "\n");
                }
                fileWriter.close();

            } catch (IOException e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
        }
        return fileNames;
    }

    public static int getNumOfLines(String[] fileNames) {
        int lines = 0;
        for (String fileName : fileNames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                while (reader.readLine() != null) {
                    lines++;
                }
            } catch (IOException e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        Thread_implement[] threads = new Thread_implement[fileNames.length];

        // Create and start a separate thread for each file
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new Thread_implement(fileNames[i]);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread_implement thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Sum the number of lines counted by each thread
        int totalLines = 0;
        for (Thread_implement thread : threads) {
            totalLines += thread.getLines();
        }
        return totalLines;
    }



    public static int getNumOfLinesThreadPool(String[] fileNames) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(fileNames.length);
        Callable_implement[] callables = new Callable_implement[fileNames.length];

        // Create a callable for each file
        for (int i = 0; i < fileNames.length; i++) {
            callables[i] = new Callable_implement(fileNames[i]);
        }

        // Invoke all callables
        List<Future<Integer>> futures = executor.invokeAll(Arrays.asList(callables));

        // Sum the number of lines counted by each callable
        int totalLines = 0;
        for (Future<Integer> future : futures) {
            try {
                totalLines += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return totalLines;
    }
}