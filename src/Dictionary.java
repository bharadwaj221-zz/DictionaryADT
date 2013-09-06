import java.io.*;
import java.sql.Timestamp;

public abstract class Dictionary{
  public abstract void insert(int value);
  public abstract boolean search(int value);
  public abstract boolean delete(int value);
  public abstract void clearADT();
  public abstract void display();
  
  void populateDictionary(String InputFileName, String TimeFileInsert)
  {
	  Timestamp time = new Timestamp(0);
	  long beginTime,endTime;
	  String [] input = null;
	  beginTime = time.getTime();
	  try {
		BufferedReader br = new BufferedReader(new FileReader(InputFileName));
		String line = br.readLine();
		input = line.split(" ");
		for(int i=0; i<input.length; i++)
			insert(Integer.parseInt(input[i]));
		br.close();
		
	} catch (FileNotFoundException e) {
		System.out.println("Invalid input file name");
	} catch (IOException e) {
		System.out.println("Invalid input file");
	}
	endTime = time.getTime();
	String timeStampEntry =  "( "+input.length+" , "+(endTime-beginTime)+" ms )";
	try {
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(TimeFileInsert, true)));
	    out.println(timeStampEntry);
	    out.close();
	}
	catch (IOException e) {
		System.out.println("Invalid input-time file");
	}
	

  }
  void locateInDictionary(String SearchFileName, String TimeFileSearch)
  {
	  Timestamp time = new Timestamp(0);
	  long beginTime,endTime;
	  String [] input = null;
	  beginTime = time.getTime();
	  try {
			BufferedReader br = new BufferedReader(new FileReader(SearchFileName));
			String line = br.readLine();
			 input= line.split(" ");
			for(int i=0; i<input.length; i++)
				if(!search(Integer.parseInt(input[i])))
					System.out.println("Element "+input[i]+" not found!!");
			br.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Invalid search file name");
		} catch (IOException e) {
			System.out.println("Invalid search file");
		}
	  endTime = time.getTime();
	  
	  String timeStampEntry =  "( "+input.length+" , "+(endTime-beginTime)+" ms )";
	  try {
		  
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(TimeFileSearch, true)));
		    out.println(timeStampEntry);
		    out.close();
	  }
	  catch (IOException e) {
			System.out.println("Invalid search-time file");
	  }
	
  }
}