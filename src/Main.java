import com.indexer.extractor.WordExtractor;
import com.indexer.index.InvertedIndex;
import com.indexer.reader.FileContentReader;
import com.indexer.scanner.DirectoryScanner;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main{
    public static void main(String args[]){

        DirectoryScanner scanner=new DirectoryScanner();
        FileContentReader reader=new FileContentReader();
        WordExtractor extractor=new WordExtractor();
        InvertedIndex index=new InvertedIndex();

        String path="testfiles";

        List<File> files=scanner.scanDirectory(path);

        System.out.println(files.size());
        int n=Runtime.getRuntime().availableProcessors();
        ExecutorService executor=Executors.newFixedThreadPool(n);


        for(File f:files){
            executor.submit(()-> {
                System.out.println("\nProcessing: " + f.getName());
                String content = reader.readFile(f);
                Set<String> words = extractor.extractWords(content);

                System.out.println(words);
                for (String token : words) {
                    index.addWord(token, f);
                }
            });
        }
        executor.shutdown();
        try{
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        Scanner sc=new Scanner(System.in);

        while(true){
            System.out.println("Enter a word to search:");
            String query=sc.nextLine();
            if(query.equalsIgnoreCase("exit")){
                break;
            }
            query=query.toLowerCase().replaceAll("[^a-zA-Z0-9 ]","");
            Set<File> results =index.search(query);
            if(results.isEmpty()){
                System.out.println("No files contain this word");
            }
            else{
                System.out.println("Found in:");

                results.stream()
                        .map(File::getName)
                        .sorted()
                        .forEach(System.out::println);
            }
        }

        sc.close();
    }
}