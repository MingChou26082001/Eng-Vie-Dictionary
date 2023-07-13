package com.example.dictionary;
import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

class Element {
    protected Trie trie;
    protected String word;

    Element(Trie trie, String word) {
        this.trie = trie;
        this.word = word;
    }
}

class Trie {
    private boolean isLeaf;
    private Map<Character, Trie> children;
    private Map<Character, Integer> character;

    Trie() {
        isLeaf = false;
        children = new HashMap<>();
        character = new HashMap<>();
    }


    @SuppressLint("NewApi")
    public void insert(String word) {
        Trie curr = this;
        for (Character ch : word.toCharArray()) {
            curr.children.putIfAbsent(ch, new Trie());
            int count = (curr.character.get(ch) == null) ? 1 : curr.character.get(ch) + 1;
            curr.character.put(ch, count);
            curr = curr.children.get(ch);
        }
        curr.isLeaf = true;
    }

    public boolean search(String word) {
        Trie curr = this;
        for (Character ch : word.toCharArray()) {
            if (curr.children.get(ch) == null)
                return false;
            curr = curr.children.get(ch);
        }
        return curr.isLeaf;
    }

    public void delete(String word) {
        if (search(word)) {
            Trie lastSecond = this;
            Character charToRemove = word.charAt(0);
            Trie curr = this;
            int i = -1;
            while (i < word.length() && curr != null) {
                if (curr.isLeaf && i != word.length() - 1) {
                    charToRemove = word.charAt(i + 1);
                    lastSecond = curr;
                }
                i = i + 1;
                if (i < word.length())
                    curr = curr.children.get(word.charAt(i));
            }
            lastSecond.children.remove(charToRemove);
        }
    }

    public int findPrefixCount(String word) {
        Trie curr = this;
        Character lastChar = null;
        int count = 0;
        for (Character ch : word.toCharArray()) {
            if (curr.children.get(ch) == null)
                return 0;
            if (count < word.length() - 1) {
                curr = curr.children.get(ch);
                count++;
            }
            lastChar = ch;
        }
        if (lastChar != null && curr.character.get(lastChar) != null)
            return curr.character.get(lastChar);
        else
            return 0;
    }

    public Set<String> autoComplete(String word) {
        Trie curr = this;
        int count = 0;
        String wo = "";
        Queue<Element> queue = new LinkedList<>();
        Set<String> set = new HashSet<>();
        for (Character ch : word.toCharArray()) {
            if (count < word.length()) {
                if(curr != null)
                    curr = curr.children.get(ch);
                else
                    return set;
                count++;
                wo += ch;
            }
        }
        if (curr != null)
            queue.add(new Element(curr, wo));

        while (!queue.isEmpty()) {
            Element elem = queue.poll();
            Trie current = elem.trie;
            String temp = elem.word;
            if (current != null && current.isLeaf)
                set.add(temp);
            @SuppressLint({"NewApi", "LocalSuppress"})
            List<Character> keys = current.character.keySet().stream().collect(Collectors.toList());
            for (int i = 0; i < current.children.size(); i++) {
                queue.add(new Element(current.children.get(keys.get(i)), temp + keys.get(i)));
            }

        }
        return set;

    }

    }

