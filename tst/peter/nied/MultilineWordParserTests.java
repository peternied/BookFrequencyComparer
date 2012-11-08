
package peter.nied;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;

public class MultilineWordParserTests extends BasicWordParserTests
{
    public MultilineWordParserTests()
    {
        mParser = new MutlilineWordParser();
    }

    @Test
    public void testHypenatedWord() throws Exception
    {
        final File file = createTempFileWithContents("word-\nboundary ");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expected to find a word", 1, wordsFoundInBook.size());
        Assert.assertEquals("Make sure we found the expected word", "word-boundary", wordsFoundInBook.get(0));
    }

    @Test
    public void testHypenatedWordAtEndOfLine() throws Exception
    {
        final File file = createTempFileWithContents("word-\nboundary");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expected to find a word", 1, wordsFoundInBook.size());
        Assert.assertEquals("Make sure we found the expected word", "word-boundary", wordsFoundInBook.get(0));
    }

    @Test
    public void testInvalidWordsWithHyphenation() throws Exception
    {
        final File file = createTempFileWithContents("\n\n a3 a3-4s 4th 4\n can-\nn1ot can1-\nnot1 2-3 .\n-. \n-a \n");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expect to not find any words", 0, wordsFoundInBook.size());
    }

    @Test
    public void testMultipleValidHypenatedWords() throws Exception
    {
        final Set<String> expectedWords = new HashSet<String>();
        expectedWords.add("a-b");
        expectedWords.add("can-not");
        expectedWords.add("baby-boy");

        final File file = createTempFileWithContents("a-b\n can-\nnot \nbaby-boy");
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
    public void testMultipleValidHypenatedWordsAlsoWithInvalids() throws Exception
    {
        final Set<String> expectedWords = new HashSet<String>();
        expectedWords.add("a-b");
        expectedWords.add("can-not");
        expectedWords.add("baby-boy");

        final File file = createTempFileWithContents("a-b\n\n a3 a3-4s 4th 4\n can-\nnot 2-3 .\n-. \n-a \nbaby-boy");
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
    public void testNoHtmlTagElementsFound() throws Exception
    {
        final File file = createTempFileWithContents("<body><table some attribute><td></td></table></body>");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expect to not find any words", 0, wordsFoundInBook.size());
    }

    @Test
    public void testNoAllCapsWordsFound() throws Exception
    {
        final File file = createTempFileWithContents("THE BROWN DOG JUMPED OVER THE MOON");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);
        Assert.assertEquals("Expect to not find any words", 0, wordsFoundInBook.size());
    }

    @Test
    public void testMultipleWordsInsideHtmlTagsFound() throws Exception
    {
        final Set<String> expectedWords = new HashSet<String>();
        expectedWords.add("cat");
        expectedWords.add("dog");
        expectedWords.add("a-b");
        expectedWords.add("can-not");
        expectedWords.add("baby-boy");

        final File file =
                createTempFileWithContents("cat <body foo > a-b\n</body>\n a3 a3-4s 4th <tag><tagger>dog</tagger></tag> 4\n can-\nnot 2-3 .\n-. \n-a \nbaby-boy");
        final IBook book = LocalBook.getNewBookFromPath(file.getAbsolutePath());
        final List<String> wordsFoundInBook = book.asWords(mParser);

        for (final String foundWord : wordsFoundInBook)
        {
            Assert.assertTrue("Expecting to find this word '" + foundWord + "' in the list of expected words",
                expectedWords.contains(foundWord));
        }
        Assert.assertEquals("Expected to find all of the words", expectedWords.size(), wordsFoundInBook.size());
    }
}
