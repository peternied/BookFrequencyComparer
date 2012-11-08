
package peter.nied;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test cases for making sure local book works as designed, using the base word parser
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
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        Assert.assertNotNull(book);
    }

    @Test
    public void testOneWordFound() throws Exception
    {
        final String singleWord = "word";
        final File file = createTempFileWithContents(singleWord);
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(new BaseWordParser());
        Assert.assertEquals("Expect to find a single word", 1, wordsFoundInBook.size());
        Assert.assertEquals("Expect to have found a word", singleWord, wordsFoundInBook.get(0));
    }

    @Test
    public void testWordWithInvalidCharacters() throws Exception
    {
        final String singleWord = "wor1d";
        final File file = createTempFileWithContents(singleWord);
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(new BaseWordParser());
        Assert.assertEquals("Expect to not find any words", 0, wordsFoundInBook.size());
    }

    @Test
    public void testMultipleValidWords() throws Exception
    {
        final Set<String> expectedWords = new HashSet<String>();
        expectedWords.add("asdf");
        expectedWords.add("qwerty");
        expectedWords.add("a");

        final File file = createTempFileWithContents("asdf a qwerty");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(new BaseWordParser());

        for (final String foundWord : wordsFoundInBook)
        {
            Assert.assertTrue("Expecting to find this word '" + foundWord + "' in the list of expected words",
                expectedWords.contains(foundWord));
        }
        Assert.assertEquals("Expected to find all of the words", expectedWords.size(), wordsFoundInBook.size());
    }

    @Test
    public void testMultipleValidWordsAlsoWithInvalids() throws Exception
    {
        final Set<String> expectedWords = new HashSet<String>();
        expectedWords.add("asdf");
        expectedWords.add("qwerty");
        expectedWords.add("a");

        final File file = createTempFileWithContents("asdf a 3aa3 a3a a3 3a ** 3*a' qwerty");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(new BaseWordParser());

        for (final String foundWord : wordsFoundInBook)
        {
            Assert.assertTrue("Expecting to find this word '" + foundWord + "' in the list of expected words",
                expectedWords.contains(foundWord));
        }
        Assert.assertEquals("Expected to find all of the words", expectedWords.size(), wordsFoundInBook.size());
    }

    @Test
    public void testNoEmptyStringWords() throws Exception
    {
        final File file = createTempFileWithContents(" \n   \t \t\t\n");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(new BaseWordParser());
        Assert.assertEquals("Expect to not find any words", 0, wordsFoundInBook.size());
    }
}
