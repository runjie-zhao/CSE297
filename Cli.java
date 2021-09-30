import java.util.*;
import java.io.*;

public class Cli {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the txt files");
		String input_string = "";
		try {
			input_string = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Please enter the useable input value");
			sc.close();
			System.exit(0);
		}
		sc.close();
		String[] filepaths = input_string.split("\\s+");
		ArrayList<Block> blocks = new ArrayList<>();
		for (String filepath : filepaths) {
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
				if (blocks.size() == 0) {
					block = new Block(tree, 0.5);
				} else {
					block = new Block(tree, blocks.get(blocks.size() - 1), 0.5);
				}
				blocks.add(block);
			} catch (IOException e) {
				System.out.println("File Cannot Be Found");
				System.exit(0);
			}
		}
		for (Block block : blocks) {
			System.out.println(block.printBlock(true));
		}
		File output = new File(filepaths[0].substring(0, filepaths[0].length() - 4) + ".block.out");
		try (BufferedWriter ostream = new BufferedWriter(new FileWriter(output))) {
			for (int i = blocks.size()-1; i >= 0; i--) {
				ostream.write(blocks.get(i).printBlock(true));
			}
			ostream.newLine();
			ostream.close();
		} catch (IOException e) {
			System.out.println("File Cannot Be Written");
				System.exit(0);
		}
	}

}
