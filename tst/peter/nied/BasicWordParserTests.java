
package peter.nied;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Test cases that make sure the basic word parser works properly
 * 
 * @author petern
 * 
 */
public class BasicWordParserTests extends BookCompareTestBase
{
    protected IWordParser mParser = new BasicWordParser();

    @Test
    public void testOneWordFound() throws Exception
    {
        final String singleWord = "word";
        final File file = createTempFileWithContents(singleWord);
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expect to find a single word", 1, wordsFoundInBook.size());
        Assert.assertEquals("Expect to have found a word", singleWord, wordsFoundInBook.get(0));
    }

    @Test
    public void testWordWithInvalidCharacters() throws Exception
    {
        final String singleWord = "wor1d";
        final File file = createTempFileWithContents(singleWord);
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
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
        final List<String> wordsFoundInBook = book.asWords(mParser);

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
        final List<String> wordsFoundInBook = book.asWords(mParser);

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
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expect to not find any words", 0, wordsFoundInBook.size());
    }
}
