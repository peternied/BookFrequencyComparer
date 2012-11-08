
package peter.nied;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final List<String> vernRawWords = vern.asWords(new MutlilineWordParser());

        final IBook poe = LocalBook.getNewBookFromPath("C:\\depot\\BookCompare\\test-content\\the_raven.htm");
        final MutlilineWordParser parser = new MutlilineWordParser();
        parser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> poeRawWords = poe.asWords(parser);

        final Map<String, Integer> vernWordMap = new HashMap<String, Integer>();
        for (final String s : poeRawWords)
        {
            String word = s.toLowerCase();

            final String[] subWords = word.split("-");
            if (subWords.length > 1)
            {
                for (String subWord : subWords)
                {
                    if (subWord != null && subWord.length() != 0)
                    {
                        incrementWordCount(vernWordMap, subWord);
                    }
                }
            }
            else
            {
                incrementWordCount(vernWordMap, word);
            }
        }

        System.out.println("Vern words: " + vernWordMap.size());
        System.out.println("The " + vernWordMap.get("the"));
        System.out.println("of " + vernWordMap.get("of"));
        System.out.println("to " + vernWordMap.get("to"));
        System.out.println("and " + vernWordMap.get("and"));

        System.out.println("Poe words: " + poeRawWords.size());
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
