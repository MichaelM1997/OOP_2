package part_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String[] files = Ex2_1.createTextFiles(3000, 2, 100000);
        long start_time, end_time;
        Ex2_1 gnolt = new Ex2_1();
        Ex2_1 gnoltp = new Ex2_1();

        start_time = System.currentTimeMillis();
        System.out.println("Num of lines: " + getNumOfLines(files));
        end_time = System.currentTimeMillis();
        System.out.println("Time for getNumOfLines: "+ (end_time - start_time) + " ms");

        start_time = System.currentTimeMillis();
        System.out.println("Num of lines: " + gnolt.getNumOfLinesThreads(files));
        end_time = System.currentTimeMillis();
        System.out.println("Time for getNumOfLinesThreads: " + (end_time - start_time) + " ms");

        start_time = System.currentTimeMillis();
        System.out.println("Num of lines: " + gnoltp.getNumOfLinesThreadPool(files));
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

    public int getNumOfLinesThreads(String[] fileNames) {
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



    public int getNumOfLinesThreadPool(String[] fileNames) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);

        // Create a list to store the Future objects corresponding to the Callable tasks
        List<Future<Integer>> list = new ArrayList<>();

        // Create a Callable task for each file
        for (String fileName : fileNames) {
            Callable<Integer> task = new Callable_implement(fileName);
            // Submit the task to the thread pool and store the Future object
            Future<Integer> future = threadPool.submit(task);
            list.add(future);
        }

        // Wait for all tasks to complete and calculate the total number of lines
        int totalLines = 0;
        for (Future<Integer> future : list) {
            totalLines += future.get();
        }

        // Shut down the thread pool
        threadPool.shutdown();

        return totalLines;
    }
}