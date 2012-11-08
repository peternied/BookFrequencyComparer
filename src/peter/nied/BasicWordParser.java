
package peter.nied;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Parser that finds words that meet the following:
 * Exclude numbers, words with numbers, punctuation, spaces, symbols
 * 
 * @author petern
 * 
 */
public class BasicWordParser implements IWordParser
{
    public List<Character> getValidWordTerminators()
    {
        return Arrays.asList('\n', ' ', '\t');
    }

    protected boolean isValidWordTerminator(final char c)
    {
        for (char validTerminator : getValidWordTerminators())
        {
            if (c == validTerminator)
            {
                return true;
            }
        }
        return false;
    }

    public List<Character> getValidWordCharacters()
    {
        List<Character> validCharacters = new LinkedList<Character>();

        // Add all lower case alphabetic characters
        for (char n = 'a'; n <= 'z'; n++)
        {
            validCharacters.add(n);
        }

        // Add all upper case alphabetic characters
        for (char n = 'A'; n <= 'Z'; n++)
        {
            validCharacters.add(n);
        }

        return validCharacters;
    }

    protected boolean isValidWordCharacter(final char c)
    {
        for (char validWordCharacter : getValidWordCharacters())
        {
            if (c == validWordCharacter)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a line and adds the selected words into the passed in collection if they meet this parsers criteria
     * 
     * @param words
     *            The collection to add words into
     * @param line
     *            The line to be parsed for words
     */
    public void parseWordFromLine(final List<String> words, final String line)
    {
        // Use this to keep track of where the start of the last word is
        int lastWordStart = 0;

        for (int i = 0; i < line.length(); i++)
        {
            final char currentChar = line.charAt(i);

            // If we have found a valid word termination then record the word assuming there was a word start
            // recorded. Once this is done, reset the last word start to the next character (It will be marked invalid
            // if this is the case when that character is reached)
            if (isValidWordTerminator(currentChar))
            {
                if (lastWordStart != -1)
                {
                    final String word = line.substring(lastWordStart, i);
                    addWordIfValid(words, word);
                }
                lastWordStart = i + 1;
            }

            // If we have found that the character is not a valid character this invalidates the word progress
            else if (!isValidWordCharacter(currentChar))
            {
                lastWordStart = -1;
            }
        }

        // it is possible that the last word on the line ended at the line end, so make sure we account for it
        if (lastWordStart != -1)
        {
            final String word = line.substring(lastWordStart, line.length());
            addWordIfValid(words, word);
        }
    }

    /**
     * Final checks that need to take place on words before they are added into the collection
     * 
     * @param words
     *            The list of words to add this word into
     * @param word
     *            The word to be added provided it passes the final checks
     */
    protected void addWordIfValid(final List<String> words, final String word)
    {
        if (word.length() == 0)
        {
            return;
        }
        words.add(word);
    }
}
