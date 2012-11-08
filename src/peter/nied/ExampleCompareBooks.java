
package peter.nied;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExampleCompareBooks
{
    /**
     * Compare the word counts of the two books:
     * A Jounery to the Interior of the Earth by Julie Vern
     * &
     * The Raven by Edgar Allan Poe"
     */
    public static void main(String[] args)
    {
        final IBook vern =
                LocalBook
                        .getNewBookFromPath("C:\\depot\\BookCompare\\test-content\\a_journey_to_the_interior_of_the_earth.htm");
        final MutlilineWordParser vernParser = new MutlilineWordParser();
        vernParser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> vernRawWords = vern.asWords(vernParser);
        final List<WordFrequency> vernWordFrequencies = getWordFrequency(vernRawWords);

        final IBook poe = LocalBook.getNewBookFromPath("C:\\depot\\BookCompare\\test-content\\the_raven.htm");
        final MutlilineWordParser parser = new MutlilineWordParser();
        parser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> poeRawWords = poe.asWords(parser);

        final List<WordFrequency> poeWordFrequencies = getWordFrequency(poeRawWords);

        System.out.println("Vern words: " + vernWordFrequencies.size());
        for (int i = 0; i < 10; i++)
        {
            final WordFrequency frequencyValue = vernWordFrequencies.get(i);
            System.out.println(frequencyValue.mWord + " " + frequencyValue.mFrequency);
        }
        System.out.println("Poe words: " + poeRawWords.size());
        for (int i = 0; i < 10; i++)
        {
            final WordFrequency frequencyValue = poeWordFrequencies.get(i);
            System.out.println(frequencyValue.mWord + " " + frequencyValue.mFrequency);
        }
    }

    private static List<WordFrequency> getWordFrequency(final List<String> poeRawWords)
    {
        // Take everything from the raw words an stick it in a map and start calculating values
        final Map<String, Integer> wordMap = new HashMap<String, Integer>();
        for (final String s : poeRawWords)
        {
            String word = s.toLowerCase();

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

    private static void incrementWordCount(final Map<String, Integer> vernWordMap, String word)
    {
        if (vernWordMap.containsKey(word))
        {
            vernWordMap.put(word, vernWordMap.get(word) + 1);
        }
        else
        {
            vernWordMap.put(word, 1);
        }
    }

}
