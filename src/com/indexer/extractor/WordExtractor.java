package com.indexer.extractor;
import java.util.HashSet;
import java.util.Set;

public class WordExtractor {
    public Set<String> extractWords(String content){
        String s=content.toLowerCase();
        s=s.replaceAll("[^a-zA-Z0-9 ]","");
        String[] words=s.split("\\s+");
        HashSet<String> set=new HashSet<>();
        for(String token:words){
            if(!token.isBlank()){
                set.add(token);
            }
        }
        return set;
    }
}
