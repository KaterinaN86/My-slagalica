package utility.slagalica;


import java.util.*;

public class LogicForSlagalicaGame {
    public static String searchLetterWithTwoCharsInWord(String lowerCaseLetter, String upperCaseLetter, String word, Map<String, Integer> lettersForWord, int letterCount) {
        while (word.contains(lowerCaseLetter) || word.contains(upperCaseLetter)) {
            if (lettersForWord.containsKey(upperCaseLetter) && lettersForWord.get(upperCaseLetter) > 0) {
                lettersForWord.put(upperCaseLetter, lettersForWord.get(upperCaseLetter) - 1);
                letterCount++;
                if (word.replace(lowerCaseLetter, "").equals(word)) {
                    word = word.replace(upperCaseLetter, "");
                } else word = word.replaceFirst(lowerCaseLetter, "");
            } else break;
        }
        return word;
    }

    public static int getLetterCount(String word, Map<String, Integer> tempLettersForWordMap, int letterCount) {
        String stringCharacter = "";
        for (Character c : word.toCharArray()) {
            c = convertCharToUppercase(c);
            stringCharacter = c.toString();
            if (tempLettersForWordMap.containsKey(stringCharacter) && tempLettersForWordMap.get(stringCharacter) != 0) {
                tempLettersForWordMap.put(stringCharacter, tempLettersForWordMap.get(stringCharacter) - 1);
                letterCount++;
            } else {
                return 0;
            }
        }
        return letterCount;
    }

    public static Character convertCharToUppercase(Character character) {
        if (character >= 'a' && character <= 'z') {
            character = (char) ((int) character - 32);
        } else if (character == 'đ') {
            character = 'Đ';
        } else if (character == 'š' || character == 'ž') {
            character = (char) ((int) character - 16);
        } else if (character == 'č') {
            character = 'Č';
        } else if (character == 'ć') {
            character = 'Ć';
        }
        return character;
    }

    public static Map<String, Integer> convertStringToHashMap(String letters) {
        HashMap<String, Integer> tmpHashMap = new HashMap<>();
        letters = convertLetterWithTwoChars("Dž", letters, tmpHashMap);
        letters = convertLetterWithTwoChars("Nj", letters, tmpHashMap);
        letters = convertLetterWithTwoChars("Lj", letters, tmpHashMap);
        for (Character c : letters.toCharArray()) {
            if (tmpHashMap.containsKey(c.toString())) {
                tmpHashMap.put(c.toString(), tmpHashMap.get(c.toString()) + 1);
            }
            tmpHashMap.putIfAbsent(c.toString(), 1);
        }
        return tmpHashMap;
    }

    public static String convertLetterWithTwoChars(String letter, String letters, Map<String, Integer> lettersHashMap) {
        while (letters.contains(letter)) {
            if (lettersHashMap.containsKey(letter)) {
                lettersHashMap.put(letter, lettersHashMap.get(letter) + 1);
            } else {
                lettersHashMap.putIfAbsent(letter, 1);
            }
            letters = letters.replaceFirst(letter, "");
        }
        return letters;
    }

    public String computersLongestWord(String lettersForWord, List<DictionaryWord> wordsDictionary) {

        String longestWord = "";
        Map<String, Integer> lettersForWordMap = convertStringToHashMap(lettersForWord);
        HashMap<String, Integer> tempLettersForWordMap = new HashMap<>();
        wordsDictionary = wordsDictionary.stream().filter(word -> word.getWordFromDictionary().length() <= lettersForWord.length()).sorted().toList();
        for (DictionaryWord dictionaryWord : wordsDictionary) {
            String word = dictionaryWord.getWordFromDictionary();
            if (word.length() < longestWord.length()) {
                continue;
            }
            tempLettersForWordMap.clear();
            tempLettersForWordMap.putAll(lettersForWordMap);
            int letterCount = 0;
            word = searchLetterWithTwoCharsInWord("nj", "Nj", word, tempLettersForWordMap, letterCount);
            word = searchLetterWithTwoCharsInWord("lj", "Lj", word, tempLettersForWordMap, letterCount);
            word = searchLetterWithTwoCharsInWord("dž", "Dž", word, tempLettersForWordMap, letterCount);

            letterCount = getLetterCount(word, tempLettersForWordMap, letterCount);
            if (letterCount > longestWord.length()) {
                longestWord = dictionaryWord.getWordFromDictionary();
            }
        }
        return longestWord;
    }

    public int findAllByWord(List<DictionaryWord> wordObjects, String userWord) {
        int result = 0;
        for (DictionaryWord word : wordObjects) {
            if (word.getWordFromDictionary().equals(userWord)) {
                result++;
            }
        }
        return result;
    }

    public int calculateNumberOfPoints(List<DictionaryWord> wordObjects, String chosenUserWord, String computerGeneratedWord) {
        int computerWordLength = computerGeneratedWord.length();
        String modifiedChosenWord = (chosenUserWord.length() > 0) ? chosenUserWord.charAt(0) + chosenUserWord.substring(1).toLowerCase() : "";
        int result = 0;
        if (findAllByWord(wordObjects, chosenUserWord.toLowerCase()) >= 1 || findAllByWord(wordObjects, modifiedChosenWord) >= 1) {
            if (computerWordLength == chosenUserWord.length()) {
                result = chosenUserWord.length() * 2 + 6;
            } else if (computerWordLength < chosenUserWord.length()) {
                result = chosenUserWord.length() * 2 + 3;
            } else {
                result = chosenUserWord.length() * 2;
            }
        }
        return result;
    }

    public Set<DictionaryWord> sortWords(List<String> readWords) {
        TreeSet<DictionaryWord> dictionaryWords = new TreeSet<>();
        readWords.forEach(word -> dictionaryWords.add(new DictionaryWord(word)));
        return dictionaryWords;
    }
}
