package mainpackage;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

/**
 * Interface for WordIndex problem realisation.
 *
 * @author Morenkov E.V.
 */
public abstract class WordIndex {
    // Index File.
    protected File f;
    // Root.
    protected final Trie root = new Trie(ROOT_SYMBOL);

    /**
     * Root symbol.
     */
    public static final Character ROOT_SYMBOL = ' ';

    /**
     * Load data from file, build index.
     *
     * @param filename - filename
     */
    abstract public void loadFile(String filename);

    /**
     * Return set of word position.
     *
     * @param searchWord - searchWord
     * @return Set of Integer
     */
    public Set<Integer> getIndexes4Word(String searchWord, boolean isCaseSensetive) {
        return getIndexes4Word(root, searchWord.toCharArray(), isCaseSensetive);
    }

    /**
     * Return set of word position.
     * CASE INSENSITIVE.
     *
     * @param searchWord - searchWord
     * @return Set of Integer
     */
    public Set<Integer> getIndexes4Word(String searchWord) {
        return getIndexes4Word(searchWord, false);
    }

    public Trie getRoot() {
        return root;
    }

    private Set<Integer> getIndexes4Word(Trie from, char[] chars, boolean isCaseSensitive) {
        if (chars.length == 0) {
            return from.getWordStart();
        } else {
            char d = chars[0];
            Set<Integer> result = null;
            char[] trimmedWord = Arrays.copyOfRange(chars, 1, chars.length);
            if (isCaseSensitive) {
                Trie childTrie = from.getTrieByValue(d);
                if (childTrie != null) {
                    return getIndexes4Word(childTrie, trimmedWord, isCaseSensitive);
                }
            } else {
                Trie lowerCaseTrie = from.getTrieByValue(Character.toLowerCase(d));
                Trie upperCaseTrie = from.getTrieByValue(Character.toUpperCase(d));
                if (lowerCaseTrie != null) {
                    result = getIndexes4Word(lowerCaseTrie, trimmedWord, isCaseSensitive);
                }
                if (upperCaseTrie != null) {
                    Set<Integer> upperCaseSet = getIndexes4Word(upperCaseTrie, trimmedWord, isCaseSensitive);
                    if (result == null) {
                        if (upperCaseSet != null) {
                            result = upperCaseSet;
                        }
                    } else {
                        if (upperCaseSet != null) {
                            result.addAll(upperCaseSet);
                        }
                    }
                }

            }
            return result;
        }
    }
}
