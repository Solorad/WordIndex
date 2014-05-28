package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Abstract class for Word Index testing.
 *
 * Created by Morenkov E.V.
 */
public abstract class WordIndexTest {
    protected String fileFrom;
    protected String fileAnswer;

    /**
     * Constructor.
     *
     * @param from - file with data
     * @param answer - file with answer
     */
    public WordIndexTest(String from, String answer) {
        this.fileFrom = from;
        this.fileAnswer = answer;
    }

    protected  Map<String, Set<Integer>> parseResultTable(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner sc = new Scanner(f);
        Map<String, Set<Integer>> result = new HashMap<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] split = line.split("[ ]+");
            Set<Integer> wordPlaces = new HashSet<>();
            for (int i = 1; i < split.length; i++) {
                wordPlaces.add(Integer.valueOf(split[i]));
            }
            result.put(split[0], wordPlaces.size() > 0 ? wordPlaces : null);
        }
        return result;
    }
}
