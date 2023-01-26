package utility.slagalica;
import org.jetbrains.annotations.NotNull;

public class DictionaryWord implements Comparable<DictionaryWord> {

    private String wordFromDictionary;

    public DictionaryWord(String wordFromDictionary) {
        this.wordFromDictionary = wordFromDictionary;
    }

    @Override
    public int compareTo(@NotNull DictionaryWord o) {
        return (Integer.compare(o.getWordFromDictionary().length(), getWordFromDictionary().length()) == 0) ? -1 : Integer.compare(o.getWordFromDictionary().length(), getWordFromDictionary().length());
    }

    public String getWordFromDictionary() {
        return wordFromDictionary;
    }

}

