
package peter.nied;

import java.util.ArrayList;
import java.util.List;

/**
 * An extension of the base word parser that retains state information so that we can detect words that are across
 * multiple lines
 * 
 * @author petern
 * 
 */
public class MutlilineWordParser extends BasicWordParser
{
    protected boolean mRecordAfterBodyTag = false;
    protected boolean mAfterBodyTag = false;
    protected boolean mInHtmlTag = false;
    protected String mPreviousWordChunk = null;

    /**
     * Allow users of this parser to specific if we should only start recording words after we've seen a body tag
     */
    public void setOnlyStartCountingAfterBodyTag(final boolean startAfterBody)
    {
        mRecordAfterBodyTag = startAfterBody;
    }

    @Override
    public List<Character> getValidWordTerminators()
    {
        final List<Character> validWordTermintors = new ArrayList<Character>(super.getValidWordTerminators());

        // The starting html tag is a way that words can be signified as started
        validWordTermintors.add('<');
        validWordTermintors.add('>');

        // Add in a host of other valid terminators. This might be good canidates to push back into the BasicWordParser
        validWordTermintors.add('[');
        validWordTermintors.add(']');
        validWordTermintors.add('{');
        validWordTermintors.add('}');
        validWordTermintors.add('\'');
        validWordTermintors.add('/');
        validWordTermintors.add('"');
        validWordTermintors.add(':');
        validWordTermintors.add(';');
        validWordTermintors.add('&');
        validWordTermintors.add('(');
        validWordTermintors.add(')');
        validWordTermintors.add('.');
        validWordTermintors.add(',');
        validWordTermintors.add('?');
        validWordTermintors.add('!');

        return validWordTermintors;
    }

    @Override
    public List<Character> getValidWordCharacters()
    {
        final List<Character> validCharacters = new ArrayList<Character>(super.getValidWordCharacters());

        // We should allow hyphen character as well as they could represent a word that is going to span multiple lines
        validCharacters.add('-');
        return validCharacters;
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

            if (currentChar == '>')
            {
                lastWordStart = -1;
                mInHtmlTag = false;
            }

            // If we are in the middle of an html tag ignore it
            if (mInHtmlTag)
            {
                continue;
            }

            // If we detect that there is a html tag starting there might be a valid word behind it
            if (currentChar == '<')
            {
                mInHtmlTag = true;

                // If we are going to use the body tag as a point of reference
                if (!mAfterBodyTag)
                {
                    try
                    {
                        System.out.println("Maybe tag: " + line.substring(i + 1, i + 5) + " '" + line.charAt(i + 5)
                            + "'");
                        if ("body".equals(line.substring(i + 1, i + 5).toLowerCase())
                            && isValidWordTerminator(line.charAt(i + 5)))
                        {
                            mAfterBodyTag = true;
                        }
                    }
                    catch (final Exception e)
                    {
                        // Ignore errors at this point
                    }
                }

            }

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
                mPreviousWordChunk = null;
            }
        }

        // it is possible that the last word on the line ended at the line end, so make sure we account for it
        if (lastWordStart != -1)
        {
            final String word = line.substring(lastWordStart, line.length());

            // If the final character of the last word has a hyphen we have a word that is spanning multiple lines
            if (word.length() != 0 && word.charAt(word.length() - 1) == '-')
            {
                mPreviousWordChunk = word;
            }
            else
            {
                addWordIfValid(words, word);
            }
        }
    }

    @Override
    protected void addWordIfValid(final List<String> words, final String word)
    {
        String currentWord = word;
        // If we have a previous word chunk on hand, it represents a word that ended with a hypen on the
        // previous line
        if (mPreviousWordChunk != null)
        {
            currentWord = mPreviousWordChunk + word;
            mPreviousWordChunk = null;
        }

        // We do not accept null or empty string characters
        if (currentWord == null || currentWord.length() == 0)
        {
            return;
        }

        // hyphenated words are only valid if the hyphen is contained within the middle of the word (Non first/last
        // positions)
        if (currentWord.charAt(0) == '-' || currentWord.charAt(currentWord.length() - 1) == '-')
        {
            return;
        }

        // Do not allow words that are all upper case to be used
        if (currentWord.equals(currentWord.toUpperCase()))
        {
            return;
        }

        // If we are not past the body tag in the document keep going
        if (mRecordAfterBodyTag && !mAfterBodyTag)
        {
            return;
        }

        super.addWordIfValid(words, currentWord);
    }
}
