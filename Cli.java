import java.util.*;
import java.math.*;
import java.io.*;

public class Cli {
    public static void main(String []args) {
        String input_string = user_input();
		String []filepaths=input_string.split("\\s+");
		ArrayList<Block> blocks = new ArrayList<>();
        for (String filepath : filepaths) {
            ArrayList<String> address = new ArrayList<>();
	    ArrayList<Integer> values = new ArrayList<>();
        //The file must have the correct txt format
		if(filepath.length()<4 || filepath.substring(filepath.length()-5).equals(".txt")) {
			System.out.println("Error: Input file does not have correct format");
			System.exit(0);
		}
		//Read from file
		File file = new File(filepath);
		BufferedReader reader;
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
		    		values.add(Integer.parseInt(arr[1]));
		    	}catch (Exception e) {
		    		System.out.println("The second value is not an integer");
		    		System.exit(0);
		    	}
		    }
		    reader.close();
            Tree tree = new Tree(address, values);
            Block block = new Block();
		}catch (IOException e) {
		   System.out.println("File Cannot Be Found");
		   System.exit(0);
		} finally {
		   if (reader != null) {
		    try {
		       reader.close();
		    } catch (IOException e1) {
		    	System.out.println("Reader Cannot Be Closed");
		        e1.printStackTrace();
		        System.exit(0);
		     }
		    }
		  }
        }

        
    }

    public static String user_input() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the txt files");
        String filename = "";
        try {
            filename = sc.nextLine();
            return filename;
        } catch (Exception e) {
            System.out.println("Please enter the useable input value");
            System.exit(0);
        }
        return filename;
    }
}
