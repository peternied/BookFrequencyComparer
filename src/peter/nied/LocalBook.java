
package peter.nied;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    @Override
    public List<String> asWords(final IWordParser parser)
    {
        final List<String> words = new LinkedList<String>();
        final List<String> lines = getFileLines();

        for (final String line : lines)
        {
            // Parse loop
            parser.parseWordFromLine(words, line);
        }

        return words;
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
