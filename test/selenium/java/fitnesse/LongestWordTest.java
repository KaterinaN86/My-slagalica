package fitnesse;

import utility.ReadFromFile;
import utility.slagalica.DictionaryWord;
import utility.slagalica.LogicForSlagalicaGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LongestWordTest {

    private String randomLetters;

    private LogicForSlagalicaGame gameLogicObject;

    public LongestWordTest() {
        gameLogicObject = new LogicForSlagalicaGame();
    }

    public String getRandomLetters() {
        return randomLetters;
    }

    public void setRandomLetters(String randomLetters) {
        this.randomLetters = randomLetters;
    }

    //Initializes expectedLongestWord. Reads from text file and finds the longest one to match generated letters.
    public String getExpectedLongestWord() {
        List<String> words = new ReadFromFile().readFromFile("https://raw.githubusercontent.com/peterjcarroll/recnik-api/master/serbian-latin.txt");
        Set<DictionaryWord> dictionaryWords = this.gameLogicObject.sortWords(words);
        return gameLogicObject.computersLongestWord(randomLetters, new ArrayList<>(dictionaryWords));
    }

    public static void main(String[] args) {
        LongestWordTest lgt = new LongestWordTest();
        lgt.setRandomLetters("IBALUMUĆOCAZ");
        System.out.println(lgt.getExpectedLongestWord());
        lgt.setRandomLetters("AROTOMANjUŽIŠ");
        System.out.println(lgt.getExpectedLongestWord());
        lgt.setRandomLetters("GORAONIEVLJT");
        System.out.println(lgt.getExpectedLongestWord());
        lgt.setRandomLetters("FOERANIUANjUŽ");
        System.out.println(lgt.getExpectedLongestWord());
    }
}
