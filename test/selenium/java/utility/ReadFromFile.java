package utility;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {


    public ArrayList<String> readFromFile(String filePath) throws IOException {
        List<String> dictionaryWords = new ArrayList<String>();
        URL url = new URL(filePath);
        BufferedReader read = new BufferedReader(
                new InputStreamReader(url.openStream()));
        List<String> words = read.lines().toList();
        for (String word : words) {
            dictionaryWords.add(word);
        }

        read.close();


        return (ArrayList<String>) dictionaryWords;
    }
}
