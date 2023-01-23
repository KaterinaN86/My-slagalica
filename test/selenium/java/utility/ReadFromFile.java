package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ReadFromFile {
    public List<String> readFromFile(String filePath) {
        URL url;
        List<String> words;
        try {
            url = new URL(filePath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedReader read;
        try {
            read = new BufferedReader(new InputStreamReader(url.openStream()));
            words = read.lines().toList();
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }
}
