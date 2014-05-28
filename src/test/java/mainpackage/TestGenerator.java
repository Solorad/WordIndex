package mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class generate resourceFile for tests from big_text.txt.
 *
 * @author Morenkov E.V.
 */
public class TestGenerator {

    /**
     * Source directory.
     */
    public static final String DIR = "src/main/resources/";

    /**
     * Case insensitive source directory.
     */
    public static final String DIR_CASE_INSENSETIVE = "src/main/resources/caseinsensitive/";

    /**
     * Case sensitive source directory.
     */
    public static final String DIR_CASE_SENSETIVE = "src/main/resources/casesensitive/";

    /**
     * Big text directory.
     */
    public static final String SOURCE_FILE_DIR = "src/main/resources/big_text.txt";


    /**
     * Method to generate files for testing.
     * Check does required files exist and if not - creates them.
     */
    public static void generateResources() {
        String sourceTest1 = DIR + "generatedSource1.txt";
        String controlSource1 = DIR_CASE_INSENSETIVE + "generatedFileResults1.txt";
        String controlSourceIns1 = DIR_CASE_SENSETIVE + "generatedFileResults1.txt";
        if (!doesFileExist(sourceTest1) || !doesFileExist(controlSource1) || !doesFileExist(controlSourceIns1)) {
            prepareTestEnvironment(SOURCE_FILE_DIR, sourceTest1, 500);
            dummyCreateResultFile(sourceTest1, controlSource1, false);
            dummyCreateResultFile(sourceTest1, controlSourceIns1, true);
        }

        String sourceTest2 = DIR + "generatedSource2.txt";
        String controlSource2 = DIR_CASE_INSENSETIVE + "generatedFileResults2.txt";
        String controlSourceIns2 = DIR_CASE_SENSETIVE + "generatedFileResults2.txt";
        if (!doesFileExist(sourceTest2) || !doesFileExist(controlSource2) || !doesFileExist(controlSourceIns2)) {
            prepareTestEnvironment(SOURCE_FILE_DIR, sourceTest2, 1000);
            dummyCreateResultFile(sourceTest2, controlSource2, false);
            dummyCreateResultFile(sourceTest2, controlSourceIns2, true);
        }

        String sourceTest3 = DIR + "generatedSource3.txt";
        String controlSource3 = DIR_CASE_INSENSETIVE + "generatedFileResults3.txt";
        String controlSourceIns3 = DIR_CASE_SENSETIVE + "generatedFileResults3.txt";
        if (!doesFileExist(sourceTest3) || !doesFileExist(controlSource3)) {
            prepareTestEnvironment(SOURCE_FILE_DIR, sourceTest3, 3000);
            dummyCreateResultFile(sourceTest3, controlSource3, false);
            dummyCreateResultFile(sourceTest3, controlSourceIns3, true);
        }

        String sourceTest4 = DIR + "HugeFile.txt";
        String controlSource4 = DIR_CASE_INSENSETIVE + "HugeFileResults.txt";
        String controlSourceIns4 = DIR_CASE_SENSETIVE + "HugeFileResults.txt";
        if (!doesFileExist(sourceTest4) || !doesFileExist(controlSource4)) {
            prepareTestEnvironment(SOURCE_FILE_DIR, sourceTest4, 60000);
            dummyCreateResultFile(sourceTest4, controlSource4, false);
            dummyCreateResultFile(sourceTest4, controlSourceIns4, true);
        }
    }

    /**
     * Build file for test with defined line numbers.
     * If source file is over and desired line number was not achieved, source file
     * starts from the beginning again.
     *
     * @param fromFileName - name of the source file
     * @param generatedFromFileName - name of file with defined line number.
     * @param lineNumber - line number
     */
    public static void prepareTestEnvironment(String fromFileName, String generatedFromFileName, int lineNumber) {
        // generate desired length file from source file.
        File fromFile = new File(fromFileName);
        Path toFile = Paths.get(generatedFromFileName);
        int curLine = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(toFile, Charset.forName("UTF-8"))) {
            while (curLine < lineNumber) {
                try (Scanner sc = new Scanner(fromFile, "UTF-8")) {
                    while (sc.hasNextLine()) {
                        if (curLine > lineNumber) {
                            break;
                        }
                        writer.append(sc.nextLine());
                        writer.newLine();
                        curLine++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates results by dummy method.
     *  @param from - file name from
     * @param to   - file name to
     * @param isSensitive - is case sensitive
     */
    public static void dummyCreateResultFile(String from, String to, boolean isSensitive) {
        File fromFile = new File(from);
        Path toFile = Paths.get(to);
        try (BufferedWriter writer = Files.newBufferedWriter(toFile, Charset.forName("UTF-8"));
             Scanner sc = new Scanner(fromFile, "UTF-8")) {
            // Digits are not count.
            sc.useDelimiter("[^A-Za-zА-Яа-яЁё']+");
            HashMap<String, List<Integer>> resultMap = new HashMap<>();
            int wordPosition = 1;
            while (sc.hasNext()) {
                String word = isSensitive ? sc.next() : sc.next().toLowerCase();
                if (resultMap.containsKey(word)) {
                    List<Integer> wordOccurrenceList = resultMap.get(word);
                    wordOccurrenceList.add(wordPosition++);
                    resultMap.put(word, wordOccurrenceList);
                } else {
                    List<Integer> wordOccurrenceList = new ArrayList<>();
                    wordOccurrenceList.add(wordPosition++);
                    resultMap.put(word, wordOccurrenceList);
                }
            }

            for (Map.Entry<String, List<Integer>> entry : resultMap.entrySet()) {
                writer.append(entry.getKey()).append(" ");
                for (Integer t : entry.getValue()) {
                    writer.append(t.toString()).append(" ");
                }
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean doesFileExist(String fileName) {
        return Files.exists(Paths.get(fileName));
    }
}
