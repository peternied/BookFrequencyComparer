
package peter.nied;

/**
 * Quick data representation used to track word frequencies
 * 
 * @author petern
 * 
 */
public class WordFrequency implements Comparable<WordFrequency>
{
    public String mWord;
    public int mFrequency;
    public int mHyphenations;

    @Override
    public int compareTo(WordFrequency o)
    {
        return o.mFrequency - mFrequency;
    }
}
