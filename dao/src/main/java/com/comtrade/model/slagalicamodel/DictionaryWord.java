package com.comtrade.model.slagalicamodel;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dictionary_words")
public class DictionaryWord implements Comparable<DictionaryWord>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wordFromDictionary;

    public DictionaryWord(String wordFromDictionary) {
        this.wordFromDictionary = wordFromDictionary;
    }

    @Override
    public int compareTo(@NotNull DictionaryWord o) {
        return Integer.compare(o.getWordFromDictionary().length(),getWordFromDictionary().length());
    }
}
