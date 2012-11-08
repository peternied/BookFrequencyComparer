
package peter.nied;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation for a book that is stored on the local file system
 * 
 * @author petern
 * 
 */
public class LocalBook implements IBook
{
    private final File mFileReference;

    private LocalBook(final String path)
    {
        if (path == null || path.equals(""))
        {
            throw new IllegalArgumentException("path cannot be null or empty");
        }

        final File tempFileReference = new File(path);
        if (!tempFileReference.exists() || !tempFileReference.canRead())
        {
            throw new IllegalStateException("Unable to read the file path");
        }

        mFileReference = tempFileReference;
    }

    /**
     * Creates a new instance of local book from a path
     * 
     * @param path
     *            The file path location to the book
     * @return The validated local book for usage
     */
    public static LocalBook getNewBookFromPath(final String path)
    {
        return new LocalBook(path);
    }

    private List<Character> validWordTerminators()
    {
        return Arrays.asList('\n', ' ', '\t');
    }

    private boolean isValidWordTerminator(final char c)
    {
        for (char validTerminator : validWordTerminators())
        {
            if (c == validTerminator)
            {
                return true;
            }
        }
        return false;
    }

    private List<Character> validWordCharacters()
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

    private boolean isValidWordCharacter(final char c)
    {
        for (char validWordCharacter : validWordCharacters())
        {
            if (c == validWordCharacter)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> asWords()
    {
        final List<String> words = new LinkedList<String>();
        final List<String> lines = getFileLines();

        for (final String line : lines)
        {
            // Parse loop
            parseWordFromLine(words, line);
        }

        return words;
    }

    private void parseWordFromLine(List<String> words, final String line)
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
                    words.add(line.substring(lastWordStart, i));
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
            words.add(line.substring(lastWordStart, line.length()));
        }
    }

    private List<String> getFileLines()
    {
        final List<String> lines = new LinkedList<String>();

        BufferedReader reader = null;
        try
        {
            final FileReader fileReader = new FileReader(mFileReference);
            reader = new BufferedReader(fileReader);
            String currentLine;
            do
            {
                currentLine = reader.readLine();
                if (currentLine == null)
                {
                    break;
                }

                // Capture the current line
                lines.add(currentLine);
            }
            // This is a little overkill as we will be broken out of by the currentline statement, but might as well
            // over protected instead of have someone change the code and break exit point becaues of a while(true)
            // block
            while (currentLine != null);

        }
        catch (IOException ioe)
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ex)
                {
                    // Ignoring this exception because it is unactionable at this point
                }
            }
            throw new RuntimeException(ioe);
        }
        return lines;
    }
}
