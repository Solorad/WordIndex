package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * WordIndex implementation. Load file in one thread.
 * Show better performance on average files.
 *
 * @author Morenkov E.V.
 */
public class WordIndexOneThreadImpl extends WordIndex {
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
            while (sc.hasNext()) {
                String t = sc.next();
                root.buildTrieByWord(wordNumber++, t.toCharArray());
            }
            // This will make the executor accept no new threads
            // and finish all existing threads in the queue
            long end = System.currentTimeMillis();
            System.out.println("Trie was build by one-threaded builder in " + (end - start) + " ms.");;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // SHOW trie internals
//        root.printTrie("");
//        System.out.println(root);
    }
}
