package part_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;

public class Callable_implement implements Callable<Integer> {

        private String fileName;

        public Callable_implement(String fileName) {

            this.fileName = fileName;
        }


    @Override
        public Integer call() {
            int lines = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                while (reader.readLine() != null) {
                    lines++;
                }
            } catch (IOException e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
            return lines;
        }
    }

