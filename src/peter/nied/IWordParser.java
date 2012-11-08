package peter.nied;

import java.util.List;

public interface IWordParser
{
    /**
     * Parses a line and adds the selected words into the passed in collection if they meet this parsers criteria
     * 
     * @param words
     *            The collection to add words into
     * @param line
     *            The line to be parsed for words
     */
    void parseWordFromLine(final List<String> words, final String line);
}
