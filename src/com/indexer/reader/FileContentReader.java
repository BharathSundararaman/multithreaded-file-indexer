package com.indexer.reader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
public class FileContentReader {
    public String readFile(File file){
         try {
             return Files.readString(file.toPath());
         }
         catch(IOException e){
              throw new RuntimeException("Error reading file:" + file.getAbsolutePath(),e);
         }
    }
}
