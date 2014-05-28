package mainpackage;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Trie implementation.
 * <p/>
 * Multithreading is supported.
 *
 * @author Morenkov E.V.
 */
public class Trie {
    private Character nodeValue;
    private Map<Character, Trie> children;
    private Set<Integer> wordStart = null;

    /**
     * Constructor. Takes character value as
     *
     * @param c - character
     */
    public Trie(Character c) {
        this.nodeValue = c;
        children = new ConcurrentHashMap<>();
    }

    /**
     * Add child node to the current.
     *
     * @param v - character
     * @return children node
     */
    public Trie addChild(Character v) {
        synchronized (this) {
            if (!children.containsKey(v)) {
                Trie child = new Trie(v);
                children.put(v, child);
                return child;
            }
        }
        return children.get(v);
    }

    public Trie getTrieByValue(Character v) {
        return children.get(v);
    }

    public void buildTrieByWord(int wordNum, char[] wordChars) {
//        System.out.println(wordChars);
        if (wordChars.length == 0) { // the end of the word.
            synchronized (this) {
                if (wordStart == null) {
                    wordStart = new ConcurrentSkipListSet<>();
                }
            }
            wordStart.add(wordNum);
            return;
        }
        Trie child = addChild(wordChars[0]);
        char[] trimmedWord = Arrays.copyOfRange(wordChars, 1, wordChars.length);
        child.buildTrieByWord(wordNum, trimmedWord);
    }

    public Set<Integer> getWordStart() {
        return wordStart;
    }


    @Override
    public String toString() {
        return "Trie{" +
                "nodeValue=" + nodeValue +
                ",\n children=" + children +
                ", wordStart=" + wordStart +
                '}';
    }

    public void printTrie(String before) {
        String now = before + nodeValue;
        if (wordStart != null && wordStart.size() != 0) {
            System.out.println(now.trim());
        }
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            entry.getValue().printTrie(now);
        }
    }

    //---------------------------------------------------------------------------------------
    //----------            NOT SUPPORTED YET
    //---------------------------------------------------------------------------------------

    /**
     * Gets nodeValue.
     *
     * @return Value of nodeValue.
     */
    private Character getNodeValue() {
        return nodeValue;
    }

    /**
     * Gets children.
     *
     * @return Value of children.
     */
    private Map<Character, Trie> getChildren() {
        return children;
    }

    /**
     * Sets new nodeValue.
     *
     * @param nodeValue New value of nodeValue.
     */
    private void setNodeValue(Character nodeValue) {
        this.nodeValue = nodeValue;
    }

    /**
     * Sets new children.
     *
     * @param children New value of children.
     */
    private void setChildren(Map<Character, Trie> children) {
        this.children = children;
    }
}
