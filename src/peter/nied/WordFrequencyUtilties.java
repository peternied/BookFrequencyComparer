
package peter.nied;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WordFrequencyUtilties
{
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

}
