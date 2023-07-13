package com.example.dictionary;

public class DictionaryModel {
    private static String words;
    private static String meanings;

    public DictionaryModel(String words, String meanings) {
        this.words = words;
        this.meanings = meanings;

    }

    public String toString(){
        return words;
    }

    public static String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public static String getMeanings() {
        return meanings;
    }

    public void setMeanings(String meanings) {
        this.meanings = meanings;
    }

}
