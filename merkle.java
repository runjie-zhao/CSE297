import java.util.*;
import java.io.*;

public class merkle{
	public String address;
	public String value;
	
	public static void main(String []args) {
		System.out.println(Read("temp.txt"));
	}
	
	public static String Read(String filepath) {
		File file = new File(filepath);
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
	    try {
	    	reader = new BufferedReader(new FileReader(file));
		    String tempStr;
		    while ((tempStr = reader.readLine()) != null) {
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