
package peter.nied;

import java.util.List;

/**
 * Generic interface for book to word translation
 * 
 * @author petern
 * 
 */
public interface IBook
{
    /**
     * Uses the parse to determine the words elements that are returned as a list
     * 
     * @return The list of works from this book. This list is expected to be in word occurrence order, with duplicated
     *         entries.
     */
    List<String> asWords();
}
