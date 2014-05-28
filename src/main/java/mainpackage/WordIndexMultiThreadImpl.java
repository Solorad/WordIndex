package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Multi thread WordIndex implementation.
 * Show better performance on huge files.
 *
 * @author Morenkov E.V.
 */
public class WordIndexMultiThreadImpl extends WordIndex {
    /**
     * {@inheritDoc}
     */
    public void loadFile(String filename) {
        f = new File(filename);
        long start = System.currentTimeMillis();
        try (Scanner sc = new Scanner(f, "UTF-8")) {
            sc.useDelimiter("[^A-Za-zА-Яа-яЁё']+");
            // We start count rows from 1 (not from 0).
            int wordNumber = 1;
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            while (sc.hasNext()) {
                final int finalNumber = wordNumber++;
                final String t = sc.next();
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        //case insensitive word
                        char[] chars = t.toCharArray();
                        root.buildTrieByWord(finalNumber, chars);
                    }
                });
            }
            // This will make the executor accept no new threads
            // and finish all existing threads in the queue
            executorService.shutdown();
            while (!executorService.isTerminated()) {
            }
            // Wait until all threads are finish
            long end = System.currentTimeMillis();
            System.out.println("Trie was build by multithread builder in " + (end - start) + " ms.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
