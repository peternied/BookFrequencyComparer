
package peter.nied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class WordFrequencyUtilties
{
    // Just use System.out for the time being, we can fix this later to output to a file or something fancy
    protected static final PrintStream mWriter = System.out;
    protected static Queue<String> mLineBuffer = new LinkedList<String>();

    /**
     * Given a list of raw words, determine the frequency of all of those words, and specfically pull hyphenated words
     * apart and take note of them
     */
    public static List<WordFrequency> getWordFrequencies(final List<String> poeRawWords)
    {
        // Take everything from the raw words an stick it in a map and start calculating values
        final Map<String, WordFrequency> wordMap = new HashMap<String, WordFrequency>();
        for (final String s : poeRawWords)
        {
            final String word = s.toLowerCase();

            // Hyphenated words should be counted separately for these purposes
            final String[] subWords = word.split("-");
            if (subWords.length > 1)
            {
                for (String subWord : subWords)
                {
                    if (subWord != null && subWord.length() != 0)
                    {
                        incrementWordCount(wordMap, subWord, true);
                    }
                }
            }
            else
            {
                incrementWordCount(wordMap, word, false);
            }
        }

        // Take the completed map of words and frequencies and stick them in a list that is easier to sort and
        // manipulate
        final List<WordFrequency> frequencies = new ArrayList<WordFrequency>(wordMap.size());
        frequencies.addAll(wordMap.values());

        Collections.sort(frequencies);

        return frequencies;
    }

    private static void incrementWordCount(final Map<String, WordFrequency> wordMap, final String word,
            final boolean wasHyphenation)
    {
        if (wordMap.containsKey(word))
        {
            final WordFrequency wf = wordMap.get(word);
            wf.mFrequency++;
            if (wasHyphenation)
            {
                wf.mHyphenations++;
            }
            wordMap.put(word, wf);
        }
        else
        {
            final WordFrequency wf = new WordFrequency();
            wf.mWord = word;
            wf.mFrequency = 1;
            if (wasHyphenation)
            {
                wf.mHyphenations = 1;
            }
            wordMap.put(word, wf);
        }
    }

    /**
     * Generates a report of the frequency and hyphenated frequency for the given two authors and then prints it to
     * standard output
     * 
     * @param maxWords
     *            This controls the maximum number of words to output for this report, is expected to be non negative
     *            and non-zero. Note if you want the max value specific -1 and this will print out the max number of
     *            words for both authors
     */
    public static void printFrequencyReport(final String author1, final List<WordFrequency> frequencies1,
            final String author2, final List<WordFrequency> frequencies2, final int maxWords)
    {
        // Handle the first rows, we are currently configured for 8 columns, with a empty column between data sets
        mLineBuffer.add(author1);
        mLineBuffer.add("");
        mLineBuffer.add("");
        mLineBuffer.add("");
        mLineBuffer.add(author2);
        mLineBuffer.add("");
        mLineBuffer.add("");
        mLineBuffer.add("");
        writeData();

        // Handle the content titles
        mLineBuffer.add("Word");
        mLineBuffer.add("Hits");
        mLineBuffer.add("Hyphens");
        mLineBuffer.add("");
        mLineBuffer.add("Word");
        mLineBuffer.add("Hits");
        mLineBuffer.add("Hyphens");
        mLineBuffer.add("");
        writeData();

        // Deal with pushing out real data
        int maxPrintedWords;
        if (maxWords != -1)
        {
            maxPrintedWords = maxWords;
        }
        else
        {
            maxPrintedWords = Math.max(frequencies1.size(), frequencies2.size());
        }

        for (int i = 0; i < maxPrintedWords; i++)
        {
            if (frequencies1.size() > i)
            {
                final WordFrequency wf1 = frequencies1.get(i);
                mLineBuffer.add(wf1.mWord);
                mLineBuffer.add(wf1.mFrequency + "");
                mLineBuffer.add(wf1.mHyphenations + "");
                mLineBuffer.add("");
            }
            else
            {
                mLineBuffer.add("");
                mLineBuffer.add("");
                mLineBuffer.add("");
                mLineBuffer.add("");
            }

            if (frequencies2.size() > i)
            {
                final WordFrequency wf2 = frequencies2.get(i);
                mLineBuffer.add(wf2.mWord);
                mLineBuffer.add(wf2.mFrequency + "");
                mLineBuffer.add(wf2.mHyphenations + "");
                mLineBuffer.add("");
            }
            else
            {
                mLineBuffer.add("");
                mLineBuffer.add("");
                mLineBuffer.add("");
                mLineBuffer.add("");
            }
            writeData();
        }
    }

    private static void writeData()
    {
        final StringBuilder sb = new StringBuilder();
        for (final String s : mLineBuffer)
        {
            sb.append(s);
            sb.append('\t');
        }

        mWriter.println(sb.toString());
        mLineBuffer.clear();
    }
}
