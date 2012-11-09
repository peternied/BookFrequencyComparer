
package peter.nied;

import java.util.List;

public class ExampleCompareBooks
{
    /**
     * Compare the word counts of the two books:
     * A Jounery to the Interior of the Earth by Julie Vern
     * &
     * The Raven by Edgar Allan Poe
     */
    public static void main(String[] args)
    {
        final IBook vern = LocalBook.getNewBookFromPath(".\\test-content\\a_journey_to_the_interior_of_the_earth.htm");
        final MutlilineWordParser vernParser = new MutlilineWordParser();
        vernParser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> vernRawWords = vern.asWords(vernParser);
        final List<WordFrequency> vernWordFrequencies = WordFrequencyUtilties.getWordFrequencies(vernRawWords);

        final IBook poe = LocalBook.getNewBookFromPath(".\\test-content\\the_raven.htm");
        final MutlilineWordParser parser = new MutlilineWordParser();
        parser.setOnlyStartCountingAfterBodyTag(true);
        final List<String> poeRawWords = poe.asWords(parser);

        final List<WordFrequency> poeWordFrequencies = WordFrequencyUtilties.getWordFrequencies(poeRawWords);

        WordFrequencyUtilties.printFrequencyReport("Vern", vernWordFrequencies, "Poe", poeWordFrequencies, -1);
    }
}
