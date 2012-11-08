
package peter.nied;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import junit.framework.TestCase;

public abstract class BookCompareTestBase extends TestCase
{
    protected File createTempFileWithContents(String contents) throws IOException
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
}
