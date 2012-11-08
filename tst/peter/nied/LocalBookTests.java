
package peter.nied;

import java.io.File;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Test cases for making sure local book works as designed, using the base word parser
 * 
 * @author petern
 * 
 */
public class LocalBookTests extends BookCompareTestBase
{
    /**
     * Make sure that we can just create a new Local Book object with valid input
     */
    @Test
    public void testCreateLocalBookInstance() throws Exception
    {
        final File file = createTempFileWithContents("");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        Assert.assertNotNull(book);
    }
}
