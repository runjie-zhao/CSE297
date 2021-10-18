import java.util.*;
import java.io.*;

public class Cli {
	static ArrayList<Block> blocks = new ArrayList<>();
	public static ArrayList<Block> read_file(String filename){
		String input = filename;
		File file = new File(input);
		BufferedReader reader;
		ArrayList<Block> block_list = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempStr;

			//Create block
			Block block = new Block();

			//Create arraylist for value and address

			ArrayList<String> address = new ArrayList<>();
			ArrayList<Integer> value = new ArrayList<>();

			while ((tempStr = reader.readLine()) != null) {
				if(tempStr.equals("BEGIN HEADER")) {
					block = new Block();
					address = new ArrayList<>();
					value = new ArrayList<>();
					continue;
				}

				//the block ends, add the block to the blocklist
				if(tempStr.equals("END BLOCK")) {
					Tree tree = new Tree(address, value);
					block.currentT = tree;
					block_list.add(block);
					block = new Block();
					continue;
				}

				if(tempStr.equals("BEGIN BLOCK")||tempStr.equals("END BLOCK")||tempStr.equals("BEGIN HEADER")) {
					continue;
				}
				if(tempStr.contains("previous block:")) {
					block.previoushash = tempStr.substring(16);
					continue;
				}else if(tempStr.contains("root:")) {
					block.hashRoot = tempStr.substring(6);
					continue;
				}else if(tempStr.contains("timestamp: ")) {
					block.timestamp = Long.valueOf(tempStr.substring(11));
					continue;
				}else if(tempStr.contains("difficulty target:")) {
					block.target = Double.valueOf(tempStr.substring(19));;
					continue;
				}else if(tempStr.contains("nonce:")) {
					block.nonce = tempStr.substring(7);
					continue;
				}else {
					String[] arr = tempStr.split("\\s+");
					if(arr.length == 2) {
						address.add(arr[0]);
						value.add(Integer.parseInt(arr[1]));
					}
					continue;
				}
			}
		} catch (IOException e) {
			System.out.println("File Cannot Be Found");
			System.exit(0);
		}
		return block_list;
	}
	
	public static void main(String[] args) {
		Scanner input  = new Scanner(System.in);
		while(true) {
			System.out.println("Please enter the option number");
			System.out.println("1. Validation");
			System.out.println("2. Balance");
			System.out.println("3. Generate a new block");
			System.out.println("0. Exit");
			String choice = input.nextLine();
			if(choice.equals("1")) {
				System.out.println("Please enter the txt files");
				String input_string1 = "";
				try {
					input_string1 = input.nextLine();
				} catch (Exception e) {
					System.out.println("Please enter the useable input value");
					System.exit(0);
				}
				try {
					ArrayList<Block> blocks1 = read_file(input_string1);
					if(blocks1.size()<2) {
						for (Block block : blocks1) {
							//System.out.println(block.printBlock(true));
							System.out.println("Validation Result is " + block.validate());
						}
					}else {
						Blockchain chain = new Blockchain(blocks1);
						boolean value = chain.validate();
						System.out.println("Validation Result is " + value);
					}
				}catch(Exception e) {
					System.out.println("Validation Result is false");
				}
			}else if(choice.equals("2")){
				Blockchain chain = new Blockchain(blocks);

				System.out.println("Please enter an address");
				String add = input.nextLine();
				boolean value = chain.balance(add);
				if(value) {
					System.out.println("Address Found");
				}else {
					System.out.println("Address Not Found");
				}

				value = chain.balance("fc91428771e2b031cd46b0478ce20a7auzi110f1");

				ArrayList<String> arr = chain.get_Res();
				for(int i = 0; i < arr.size(); i++) {
					System.out.println(arr.get(i));
				}
			}else if(choice.equals("3")) {
				System.out.println("Please enter the txt files");
				String input_string1 = "";
				try {
					input_string1 = input.nextLine();
				} catch (Exception e) {
					System.out.println("Please enter the useable input value");
					System.exit(0);
				}
				String[] filepaths1 = input_string1.split("\\s+");
				ArrayList<Block> blocks1 = new ArrayList<>();
				for (String filepath : filepaths1) {
					ArrayList<String> address = new ArrayList<>();
					ArrayList<Integer> values = new ArrayList<>();
					// The file must have the correct txt format
					if (filepath.length() < 4 || filepath.substring(filepath.length() - 5).equals(".txt")) {
						System.out.println("Error: Input file does not have correct format");
						System.exit(0);
					}
					// Read from file
					File file = new File(filepath);
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader(file));
						String tempStr;

						while ((tempStr = reader.readLine()) != null) {
							// Test if each line is composed of two value
							String[] arr = tempStr.split("\\s+");
							if (arr.length >= 3) {
								System.out.println(
										"The format in .txt file is wrong. Each line should be consist of 1 address and 1 value.");
								System.exit(0);
							}
							// Assign the value of arr[0] to address
							address.add(arr[0]);
							// Use try catch to test if the second value is an integer
							try {
								values.add(Integer.parseInt(arr[1]));
							} catch (Exception e) {
								System.out.println("The second value is not an integer");
								System.exit(0);
							}
						}
						reader.close();
						Tree tree = new Tree(address, values);
						Block block;
						if (blocks1.size() == 0) {
							block = new Block(tree, 0.5);
						} else {
							block = new Block(tree, blocks1.get(blocks1.size() - 1), 0.5);
						}
						blocks1.add(block);
					} catch (IOException e) {
						System.out.println("File Cannot Be Found");
						System.exit(0);
					}
				}
				for (Block block : blocks1) {
					System.out.println(block.printBlock(true));
				}
				File output1 = new File(filepaths1[0].substring(0, filepaths1[0].length() - 4) + ".block.out");
				try (BufferedWriter ostream = new BufferedWriter(new FileWriter(output1))) {
					for (int i = blocks1.size()-1; i >= 0; i--) {
						ostream.write(blocks1.get(i).printBlock(true));
					}
					ostream.newLine();
					ostream.close();
				} catch (IOException e) {
					System.out.println("File Cannot Be Written");
						System.exit(0);
				}
				blocks = blocks1;
			}else if(choice.equals("0")) {
				break;
			}else {
				System.out.println("Please enter a correct option");
			}
		}
	}
}
