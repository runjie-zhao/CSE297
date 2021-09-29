import java.util.*;
import java.io.*;

public class merkle{
	//Initialize three variables which are address, value and mkblock
	public static ArrayList<String> address = new ArrayList<>();
	public static ArrayList<Integer> value = new ArrayList<>();
	public static ArrayList<merkleblock> mkblock = new ArrayList<>();
	public static ArrayList<merkleblock> rootblock = new ArrayList<>();
	public static ArrayList<ArrayList<merkleblock>> allblock = new ArrayList<>();
	
	public static void main(String []args) {
		String filepath = user_input();
		String []arr=filepath.split("\\s+");
		
		//Create the target
		
		String eval = "";
		for(int i = 0; i < 64; i++) {
			if(i==0) {
				eval = eval + "7";
			}else {
				eval = eval + "f";
			}
		}
		
		int counter = 0;
		for(String ss : arr) { 
			Read(ss);
			//Iterate the mkblock and show the value
			for(int i = 0; i < mkblock.size(); i++) {
				//System.out.println(mkblock.get(i).address + " " + mkblock.get(i).value);
				String code = mkblock.get(i).create_hash();
				mkblock.get(i).hash = code;
				//System.out.println(mkblock.get(i).hash);
			}
			allblock.add(mkblock);
			
			//Define the root block
			String finalval = get_root(mkblock);
			merkleblock rootb = new merkleblock(finalval);
			rootblock.add(rootb);
			//Used for test nonce
			
			
			/*char carr[] = new char[64];
			for(int i = 0; i < 64; i++) {
				carr[i]='0';
			}
			
			boolean judge = false;
			for(int i = 0; i < 64; i++) {
				for(int j = 0; j < 16; j++) {
					String val = Integer.toHexString(j);
					carr[63-i]=val.charAt(0);
					String val1 = new String(carr);
					String res = mkblock.get(0).test(val1,finalval);
					if(res.compareTo(eval)<0) {
						System.out.println("Our nonce is " + val1 + "\n And result is " + res + "\n Gap" + eval);
						judge = true;
						break;
					}
				}
				if(judge) {
					break;
				}
			}*/
			
			
			//System.out.println("char "+carr[1]);
			//int result = Integer.parseInt(temp,16);
			//System.out.println("16=>"+result);
			//String res = mkblock.get(0).test(temp,finalval);
			//System.out.println("Result after nonce is "+ res);
			//Test ends
			
			//System.out.println("Final Root is: ");
			//System.out.println(finalval);
		}
		nonce_choice(eval);
		header_create();
		for(int i = 0; i < rootblock.size(); i++) {
			//System.out.println(mkblock.get(i).address + " " + mkblock.get(i).value);
			//String code = mkblock.get(i).create_hash();
			//mkblock.get(i).hash = code;
			//System.out.println(mkblock.get(i).hash);
			System.out.println("Header of block "+(i+1)+" is " + rootblock.get(i).header);
		}
	}
	
	
	//Read from txt file
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
		    
		    //test
		    mkblock = new ArrayList<>();
		    //test ends
		    
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
		    	//Create the merkle tree leaves and assign the value to it.
		    	Assign(arr[0],Integer.parseInt(arr[1]));
		    	sbf.append(tempStr);
		    }
		    reader.close();
		    return sbf.toString();
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
		  return sbf.toString();
	}
	
	
	//Create a new merkleblock class and assign the address and value to it.
	public static void Assign(String address, int value) {
		merkleblock mk = new merkleblock(address, value);
		mkblock.add(mk);
	}
	
	//Get hash from left and right and store all final values in an array
	public static ArrayList<merkleblock> get_hash(ArrayList<merkleblock> mk_arr) {
		ArrayList<merkleblock> new_arr = new ArrayList<>();
		for(int i = 0; i < mk_arr.size();i++) {
			//Store the left value at first
			String left_hash = mk_arr.get(i).hash;
			//Move i to the next index
			i++;
			String right_hash = "";
			if(i < mk_arr.size()) {
				right_hash = mk_arr.get(i).hash;
			}
			merkleblock temp = new merkleblock();
			temp.create_hash(right_hash, left_hash);
			new_arr.add(temp);
		}
		return new_arr;
	}
	
	//Figure out the root
	public static String get_root(ArrayList<merkleblock> mk_arr) {
		ArrayList<merkleblock> arr = new ArrayList<>();
		ArrayList<merkleblock> final_arr = new ArrayList<>();
		arr = get_hash(mk_arr);
		while(arr.size()!=1) {
			arr = get_hash(arr);
		}
		return arr.get(0).hash;
	}
	
	public static String user_input() {
		Scanner sc =  new Scanner(System.in);
		System.out.println("Please enter the txt files");
		String filename = "";
		try {
			filename = sc.nextLine();
			return filename;
		}catch(Exception e) {
			System.out.println("Please enter the useable input value");
			System.exit(0);
		}
		return filename;
	}
	
	//choose the nonce
	public static String nonce_choice(String target) {
		System.out.println("\nNumber of block or input txt file are/is "+rootblock.size());
		char carr[] = new char[64];
		for(int i = 0; i < 64; i++) {
			carr[i]='0';
		}
		boolean judge = false;
		for(int k = 0; k < rootblock.size();k++) {
			rootblock.get(k).target = target;
			judge = false;
			for(int i = 0; i < Integer.MAX_VALUE; i++) {
				String val = Integer.toHexString(i);
				
				String res = rootblock.get(k).test(val,rootblock.get(k).hash);
				if(res.compareTo(target)<=0) {
					System.out.println("Result of (nonce + root hash)'s sha256 is " + res + "\n" + "Value of nonce " + val + "\n");
					rootblock.get(k).nonce = val;
					judge = true;
					break;
				}
				if(judge) {
					break;
				}
			}
				
		}
		
		return target;
	}
	
	//Create header
	public static void header_create() {
		for(int i = 0; i < rootblock.size(); i++) {
			if(i==0) {
				rootblock.get(i).header="0";
			}else {
				String value = rootblock.get(i).previoushash + rootblock.get(i).hash + rootblock.get(i).timestamp + rootblock.get(i).target;
				rootblock.get(i).header = rootblock.get(i).create_hash(value,rootblock.get(i).nonce);
			}
		}
	}

	public static void printConsole(merkleblock block) {
		System.out.println(block);
	}
}