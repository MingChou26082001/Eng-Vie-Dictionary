package com.example.dictionary;

public class HistoryModel {
    private static String words;


    public HistoryModel(String words) {
        this.words = words;

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

}
