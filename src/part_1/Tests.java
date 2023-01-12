package part_1;

import org.junit.Assert;
import org.junit.Test;
import part_1.Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Tests {

    @Test
    public void testCreateTextFiles() {
        String[] files = Ex2_1.createTextFiles(1000, 2, 100000);
        // Verify that the number of files created is correct
        Assert.assertEquals(1000, files.length);
        // Verify that the files are created with the correct name format
        for (int i = 0; i < files.length; i++) {
            Assert.assertEquals("file_num_" + (i + 1) + ".txt", files[i]);
        }
    }

    @Test
    public void testGetNumOfLines() {
        // Create some test files
        String[] files = Ex2_1.createTextFiles(1000, 2, 100000);
        int expectedLines = 0;
        for (String file : files) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (reader.readLine() != null) {
                    expectedLines++;
                }
            } catch (IOException e) {
                // Handle exception
            }
        }
        // Test the method getNumOfLines()
        int actualLines = Ex2_1.getNumOfLines(files);
        Assert.assertEquals(expectedLines, actualLines);
    }

    @Test
    public void testGetNumOfLinesThreads() {
        // Create some test files
        String[] files = Ex2_1.createTextFiles(1000, 2, 100000);
        int expectedLines = 0;
        for (String file : files) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (reader.readLine() != null) {
                    expectedLines++;
                }
            } catch (IOException e) {
                // Handle exception
            }
        }
        // Test the method getNumOfLinesThreads()
        Ex2_1 ex2_1 = new Ex2_1();
        int actualLines = ex2_1.getNumOfLinesThreads(files);
        Assert.assertEquals(expectedLines, actualLines);
    }

    @Test
    public void testGetNumOfLinesThreadPool() throws InterruptedException, ExecutionException {
        // Create some test files
        String[] files = Ex2_1.createTextFiles(1000, 2, 100000);
        int expectedLines = 0;
        for (String file : files) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (reader.readLine() != null) {
                    expectedLines++;
                }
            } catch (IOException e) {
                // Handle exception
            }
        }
        // Test the method getNumOfLinesThreadPool()
        Ex2_1 ex2_1 = new Ex2_1();
        int actualLines = ex2_1.getNumOfLinesThreadPool(files);
        Assert.assertEquals(expectedLines, actualLines);
    }
}

