package utility;


import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FindWordForSlagalicaGame {

    public String computersLongestWord(String lettersForWord, List<String> wordsDictionary) {

        String longestWord = "";
        HashMap<String,Integer> lettersForWordMap=convertStringToHashMap(lettersForWord);
        HashMap<String, Integer> temp=new HashMap<>();
        wordsDictionary=wordsDictionary.stream().filter((word)-> word.length()<=lettersForWord.length()).sorted().collect(Collectors.toList());
        for (String dictionaryWord : wordsDictionary) {
            String word = dictionaryWord;
            if (word.length() < longestWord.length()) {
                continue;
            }

            temp.clear();
            temp.putAll(lettersForWordMap);
            int letterCount=0;
            String stringCharacter="";
            while(word.contains("nj") || word.contains("Nj")){
                if(temp.containsKey("Nj") && temp.get("Nj")>0){
                    temp.put("Nj",temp.get("Nj")-1);
                    letterCount++;
                    if(word.replace("nj","").equals(word)){
                        word=word.replace("Nj","");
                    }
                    else word=word.replaceFirst("nj","");
                }
                else break;
            }
            while(word.contains("lj") || word.contains("Lj")){
                if(temp.containsKey("Lj") && temp.get("Lj")>0){
                    temp.put("Lj",temp.get("Lj")-1);
                    letterCount++;
                    if(word.replace("lj","").equals(word)){
                        word=word.replace("Lj","");
                    }
                    else word=word.replaceFirst("lj","");
                }
                else break;
            }

            while(word.contains("dž") || word.contains("Dž")){
                if(temp.containsKey("Dž") && temp.get("Dž")>0){
                    temp.put("Dž",temp.get("Dž")-1);
                    letterCount++;
                    if(word.replace("dž","").equals(word)){
                        word=word.replace("Dž","");
                    }
                    else word=word.replaceFirst("dž","");
                }
                else break;
            }

            for (Character c:  word.toCharArray()) {
                //convert char to uppercase
                if(c >= 'a' && c <= 'z') {
                    c = (char) ((int)c - 32);
                }
                else if(c == 'đ'){
                    c='Đ';
                } else if (c=='š' || c=='ž') {
                    c = (char) ((int)c-16);
                } else if(c=='č') {
                    c='Č';
                } else if (c=='ć') {
                    c='Ć';
                }
                stringCharacter=c.toString();
                if (temp.containsKey(stringCharacter) && temp.get(stringCharacter)!=0){
                    temp.put(stringCharacter,temp.get(stringCharacter)-1);
                    letterCount++;
                }
                else {
                    letterCount=0;
                    break;
                }
            }
            if (letterCount > longestWord.length()) {
                longestWord = dictionaryWord;
            }
        }

        return longestWord;
    }

    public static HashMap<String,Integer> convertStringToHashMap(String letters){
        HashMap<String,Integer> tmpHashMap=new HashMap<>();
        while (letters.contains("Dž")){
            if(tmpHashMap.containsKey("Dž")){
                tmpHashMap.put("Dž",tmpHashMap.get("Dž")+1);
            }
            else{
                tmpHashMap.putIfAbsent("Dž",1);
            }
            letters=letters.replaceFirst("Dž","");
        }
        while (letters.contains("Nj")){
            if(tmpHashMap.containsKey("Nj")){
                tmpHashMap.put("Nj",tmpHashMap.get("Nj")+1);
            }
            else{
                tmpHashMap.putIfAbsent("Nj",1);
            }
            letters=letters.replaceFirst("Nj","");
        }
        while (letters.contains("Lj")){
            if(tmpHashMap.containsKey("Lj")){
                tmpHashMap.put("Lj",tmpHashMap.get("Lj")+1);
            }
            else{
                tmpHashMap.putIfAbsent("Lj",1);
            }
            letters=letters.replaceFirst("Lj","");
        }

        for (Character c:  letters.toCharArray()) {
            if(tmpHashMap.containsKey(c.toString())){
                tmpHashMap.put(c.toString(),tmpHashMap.get(c.toString())+1);
            }
            tmpHashMap.putIfAbsent(c.toString(),1);
        }
        return tmpHashMap;
    }
}
