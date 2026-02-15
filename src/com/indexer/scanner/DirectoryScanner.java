package com.indexer.scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class DirectoryScanner {
    public List<File> scanDirectory(String path){
          File dir=new File(path);
          if(!dir.exists() || !dir.isDirectory())
              throw new IllegalArgumentException("Invalid Directory path");
          File[] files=dir.listFiles();
          if(files==null)
              return new ArrayList<>();
          List<File> validFiles=new ArrayList<>();
          for(File n:files){
              if(n.isFile()&& n.canRead() && n.getName().toLowerCase().endsWith(".txt"))
                  validFiles.add(n);
          }
          return validFiles;
    }
}
