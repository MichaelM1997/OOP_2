import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Thread_implement extends Thread {

        private String fileName;
        private int lines;

        public Thread_implement(String fileName) {
            this.fileName = fileName;
            this.lines = 0;
        }

        public int getLines() {

            return lines;
        }

        @Override
        public void run() {
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
    }

