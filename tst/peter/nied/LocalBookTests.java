
package peter.nied;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test cases for making sure local book works as designed
 * 
 * @author petern
 * 
 */
public class LocalBookTests extends TestCase
{
    private File createTempFileWithContents(String contents) throws IOException
    {
        // Create any random file name
        final File tempFile = File.createTempFile("testfile", ".txt");

        // Make sure that these files will clean up after themselves
        tempFile.deleteOnExit();

        final FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.append(contents);
        fileWriter.close();
        return tempFile;
    }

    /**
     * Make sure that we can just create a new Local Book object with valid input
     */
    @Test
    public void testCreateLocalBookInstance() throws Exception
    {
        final File file = createTempFileWithContents("");
        LocalBook.getNewBookFromPath(file.getAbsolutePath());
    }
}
