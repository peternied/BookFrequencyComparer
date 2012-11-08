
package peter.nied;

public class WordFrequency implements Comparable<WordFrequency>
{
    public String mWord;
    public int mFrequency;

    @Override
    public int compareTo(WordFrequency o)
    {
        return o.mFrequency - mFrequency;
    }
}
