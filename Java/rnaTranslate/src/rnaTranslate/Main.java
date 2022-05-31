package rnaTranslate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args) throws IOException {
    	
    	//Load in the dictionary
        ReadFile.main((String[]) null);
        Map<String, String> dict = ReadFile.getMap(); 
        
        //Get user input and turn it to uppercase
        String sequence = getUserInput();
        
        //Store chunked strings into list
        List<String> seq_chunks = chunkString(sequence, 3);
        
        //Clean dictionary (replace "Stop" to "_")
        //for each (key,value) --> if value.equals("Stop") --> dict.replace(key, "_")
        dict.forEach((k,v) -> {
        	  if (v.equals("Stop")) dict.replace(k, "_");
        	});
        
        //Remove chunks that aren't of length 3
        seq_chunks.removeIf(s -> s.length() < 3);
       
        //Translate using Map
        String translated = "";
        for (String s : seq_chunks) {	
        	translated += dict.get(s);
        }
        
        System.out.print(translated);
       
    }
    
    public static String getUserInput() {
    	Scanner myObj = new Scanner(System.in);
    	System.out.println("Enter RNA sequence: ");
    	String sequence = myObj.nextLine();
    	sequence = sequence.toUpperCase();
    	
		return sequence;
    	
    }
    
    public static List<String> chunkString(String s, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < s.length(); i += chunkSize) {
            chunks.add(s.substring(i, Math.min(s.length(), i + chunkSize)));
        }
        return chunks;
    }
    
   }

