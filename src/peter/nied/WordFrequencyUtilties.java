
package peter.nied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

public class WordFrequencyUtilties
{
    // Just use System.out for the time being, we can fix this later to output to a file or something fancy
    protected static final PrintStream mWriter = System.out;
    protected static Queue<String> mLineBuffer = new LinkedList<String>();
    
    public static List<WordFrequency> getWordFrequencies(final List<String> poeRawWords)
    {
        // Take everything from the raw words an stick it in a map and start calculating values
        final Map<String, Integer> wordMap = new HashMap<String, Integer>();
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
                        incrementWordCount(wordMap, subWord);
                    }
                }
            }
            else
            {
                incrementWordCount(wordMap, word);
            }
        }

        // Take the completed map of words and frequencies and stick them in a list that is easier to sort and
        // manipulate
        final List<WordFrequency> frequencies = new ArrayList<WordFrequency>(wordMap.size());
        for (final Entry<String, Integer> entry : wordMap.entrySet())
        {
            final WordFrequency frequencyEntry = new WordFrequency();
            frequencyEntry.mWord = entry.getKey();
            frequencyEntry.mFrequency = entry.getValue();
            frequencies.add(frequencyEntry);
        }

        Collections.sort(frequencies);

        return frequencies;
    }

    private static void incrementWordCount(final Map<String, Integer> wordMap, final String word)
    {
        if (wordMap.containsKey(word))
        {
            wordMap.put(word, wordMap.get(word) + 1);
        }
        else
        {
            wordMap.put(word, 1);
        }
    }

    public static void printFrequencyReport(final String author1, final List<WordFrequency> frequencies1,
            final String author2, final List<WordFrequency> frequencies2)
    {
        // Handle the first rows, we are currently configured for 6 colums, with a empty column between datasets
        mLineBuffer.add(author1);
        mLineBuffer.add("");
        mLineBuffer.add("");
        mLineBuffer.add(author2);
        mLineBuffer.add("");
        mLineBuffer.add("");
        writeData();

        // Handle the content titles
        mLineBuffer.add("Word");
        mLineBuffer.add("Hits");
        mLineBuffer.add("");
        mLineBuffer.add("Word");
        mLineBuffer.add("Hits");
        mLineBuffer.add("");
        writeData();

        // Deal with pushing out real data
        for (int i = 0; i < 10; i++)
        {
            if (frequencies1.size() > i)
            {
                final WordFrequency wf1 = frequencies1.get(i);
                mLineBuffer.add(wf1.mWord);
                mLineBuffer.add(wf1.mFrequency + "");
                mLineBuffer.add("");
            }
            else
            {
                mLineBuffer.add("");
                mLineBuffer.add("");
                mLineBuffer.add("");
            }

            if (frequencies2.size() > i)
            {
                final WordFrequency wf2 = frequencies2.get(i);
                mLineBuffer.add(wf2.mWord);
                mLineBuffer.add(wf2.mFrequency + "");
                mLineBuffer.add("");
            }
            else
            {
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
