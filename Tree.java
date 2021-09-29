import java.util.*;

//import org.w3c.dom.Node;

import java.math.*;
import java.io.*;

public class Tree{
	//Initialize three variables which are address, value and mkblock
	private ArrayList<Node> node_list = new ArrayList<>();
	private Node rootnode = new Node();
	public static ArrayList<Node> mkblock = new ArrayList<>();
	public static ArrayList<Node> rootblock = new ArrayList<>();
	public static ArrayList<ArrayList<Node>> allblock = new ArrayList<>();
	
	public Tree(ArrayList<String> address, ArrayList<Integer> value) {
		for(int i = 0; i < address.size(); i++) {
			Node node = new Node(address.get(i),value.get(i));
			node.create_hash();
			node_list.add(node);
		}
		rootnode.hash = get_root(node_list);
		
	}
	
	public Node get_rootNode() {
		return rootnode;
	}
	
	public ArrayList<Node> get_list(){
		return node_list;
	}
	
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
			Node rootb = new Node(finalval);
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
		Node mk = new Node(address, value);
		mkblock.add(mk);
	}
	
	//Get hash from left and right and store all final values in an array
	public static ArrayList<Node> get_hash(ArrayList<Node> mk_arr) {
		ArrayList<Node> new_arr = new ArrayList<>();
		for(int i = 0; i < mk_arr.size();i++) {
			//Store the left value at first
			String left_hash = mk_arr.get(i).hash;
			//Move i to the next index
			i++;
			String right_hash = "";
			if(i < mk_arr.size()) {
				right_hash = mk_arr.get(i).hash;
			}
			Node temp = new Node();
			temp.create_hash(right_hash, left_hash);
			new_arr.add(temp);
		}
		return new_arr;
	}
	
	//Figure out the root
	public static String get_root(ArrayList<Node> mk_arr) {
		ArrayList<Node> arr = new ArrayList<>();
		ArrayList<Node> final_arr = new ArrayList<>();
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
			while(!judge){
				int i = (int)(Math.random()*10000);
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

	public static void printBlock(Node block) {
		System.out.println(block);
	}
}