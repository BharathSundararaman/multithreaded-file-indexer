package com.indexer.index;
import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InvertedIndex {
    private Map<String,Set<File>> index;
    public InvertedIndex(){
        index=new ConcurrentHashMap<>();
    }
    public void addWord(String word,File file){
        index.computeIfAbsent(word,k -> ConcurrentHashMap.newKeySet()).add(file);
    }

    public Set<File> search(String word){
        word=word.toLowerCase();
        return index.getOrDefault(word,Collections.emptySet());
    }
}
