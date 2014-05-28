package mainpackage;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/**
 * Test for Word Index implementations.
 *
 * @author Morenkov E.V.
 */
@RunWith(Parameterized.class)
public class WordIndexSensitiveTest extends WordIndexTest{

    private static final List<Object[]> TESTS_;

    static {
        TESTS_ = new ArrayList<>();
        addTest("src/main/resources/generatedSource1.txt",
                "src/main/resources/casesensitive/generatedFileResults1.txt");
        addTest("src/main/resources/generatedSource2.txt",
                "src/main/resources/casesensitive/generatedFileResults2.txt");
        addTest("src/main/resources/generatedSource3.txt",
                "src/main/resources/casesensitive/generatedFileResults3.txt");
        addTest("src/main/resources/HugeFile.txt",
                "src/main/resources/casesensitive/HugeFileResults.txt");
    }

    @BeforeClass
    public static void beforeClass() {
        TestGenerator.generateResources();
    }


    public WordIndexSensitiveTest(String fileFrom, String fileAnswer) {
        super(fileFrom, fileAnswer);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return TESTS_;
    }

    @Test
    public void testMultithreadThreadImpl() throws Exception {
        WordIndexMultiThreadImpl multiThreadImpl = new WordIndexMultiThreadImpl();
        multiThreadImpl.loadFile(fileFrom);
        Map<String, Set<Integer>> stringListMap = parseResultTable(fileAnswer);
        for (Map.Entry<String, Set<Integer>> entry : stringListMap.entrySet()) {
            Set<Integer> indexes4WordSet = multiThreadImpl.getIndexes4Word(entry.getKey(), true);
            if (entry.getValue() != null) {
                Assert.assertTrue(entry.getValue().equals(indexes4WordSet));
            } else {
                Assert.assertNull(indexes4WordSet);
            }
        }
    }

    @Test
    public void testOneThreadImpl() throws Exception {
        WordIndexOneThreadImpl multiThreadImpl = new WordIndexOneThreadImpl();
        multiThreadImpl.loadFile(fileFrom);
        Map<String, Set<Integer>> stringListMap = parseResultTable(fileAnswer);
        for (Map.Entry<String, Set<Integer>> entry : stringListMap.entrySet()) {
            Set<Integer> indexes4WordSet = multiThreadImpl.getIndexes4Word(entry.getKey(), true);
            if (entry.getValue() != null) {
                Assert.assertTrue(entry.getValue().equals(indexes4WordSet));
            } else {
                Assert.assertNull(indexes4WordSet);
            }
        }
    }

    private static void addTest(String fileFrom, String fileTo) {
        TESTS_.add(new Object[] {fileFrom, fileTo});
    }
}
