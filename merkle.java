import java.util.*;
import java.io.*;

public class merkle{
	public static ArrayList<String> address = new ArrayList<>();
	public static ArrayList<Integer> value = new ArrayList<>();
	
	public static void main(String []args) {
		System.out.println(Read("xt.txt"));
		System.out.println();
		System.out.println("Elements are" + address);
		System.out.println();
		System.out.println("Elements integer are " + value);
	}
	
	public static String Read(String filepath) {
		//The file must have the correct txt format
		if(filepath.length()<4 || filepath.substring(filepath.length()-5).equals(".txt")) {
			System.out.println("Error: Input file does not have correct format");
			System.exit(0);
		}
		//Read from file
		File file = new File(filepath);
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
	    try {
	    	reader = new BufferedReader(new FileReader(file));
		    String tempStr;
		    while ((tempStr = reader.readLine()) != null) {
		    	//Test if each line is composed of two value
		    	String []arr = tempStr.split("\\s+");
		    	if(arr.length>=3) {
		    		System.out.println("The format in .txt file is wrong. Each line should be consist of 1 address and 1 value.");
		    		System.exit(0);
		    	}
		    	//Assign the value of arr[0] to address
		    	address.add(arr[0]);
		    	//Use try catch to test if the second value is an integer
		    	try {
		    		value.add(Integer.parseInt(arr[1]));
		    	}catch (Exception e) {
		    		System.out.println("The second value is not an integer");
		    		System.exit(0);
		    	}
		    	sbf.append(tempStr);
		    }
		    reader.close();
		    return sbf.toString();
		}catch (IOException e) {
		   System.out.println("File Cannot Be Found");
		   e.printStackTrace();
		} finally {
		   if (reader != null) {
		    try {
		       reader.close();
		    } catch (IOException e1) {
		    	System.out.println("Reader Cannot Be Closed");
		        e1.printStackTrace();
		     }
		    }
		  }
		  return sbf.toString();
	}
	
}