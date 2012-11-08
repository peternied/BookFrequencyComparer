
package peter.nied;

import java.util.List;

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
        final IBook vern = LocalBook.getNewBookFromPath(".\\test-content\\a_journey_to_the_interior_of_the_earth.htm");
        final MutlilineWordParser vernParser = new MutlilineWordParser();
        vernParser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> vernRawWords = vern.asWords(vernParser);
        final List<WordFrequency> vernWordFrequencies = WordFrequencyUtilties.getWordFrequencies(vernRawWords);

        System.out.println("Vern words: " + vernWordFrequencies.size());
        for (int i = 0; i < 10; i++)
        {
            final WordFrequency frequencyValue = vernWordFrequencies.get(i);
            System.out.println(frequencyValue.mWord + " " + frequencyValue.mFrequency);
        }

        final IBook poe = LocalBook.getNewBookFromPath(".\\test-content\\the_raven.htm");
        final MutlilineWordParser parser = new MutlilineWordParser();
        parser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> poeRawWords = poe.asWords(parser);

        final List<WordFrequency> poeWordFrequencies = WordFrequencyUtilties.getWordFrequencies(poeRawWords);

        System.out.println("Poe words: " + poeRawWords.size());
        for (int i = 0; i < 10; i++)
        {
            final WordFrequency frequencyValue = poeWordFrequencies.get(i);
            System.out.println(frequencyValue.mWord + " " + frequencyValue.mFrequency);
        }
    }

}
