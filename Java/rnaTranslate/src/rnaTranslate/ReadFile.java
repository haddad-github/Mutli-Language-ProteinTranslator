package rnaTranslate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadFile {
	
	public static Map<String, String> map = new HashMap<String, String>();
	
	public static void main(String[] args) throws IOException {
		
		//path
		String path = "C:\\Users\\rorop\\Desktop\\Script_In_All_Languages\\codon_table.csv";
		
		//lines read from the CSV
		String line = "";
		
		//read file
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			//keep counter of lines
			int count = 0;
			//while line is not null, store
			while( (line = br.readLine()) != null) {
				
				//skip first line
				if(count != 0) {
				
					//split by comma
					String[] values = line.split(",");
					
					//store values[0] as keys and values[2] as values
					map.put(values[0], values[2]);
				}
				
				//increment line
				count++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
    public static Map<String, String> getMap() {
        return map;
    }
}
