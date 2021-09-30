import java.util.*;

public class Tree {
	// Initialize three variables which are address, value and mkblock
	public ArrayList<Node> node_list;
	public Node rootnode;
	// public static ArrayList<Node> mkblock = new ArrayList<>();
	// public static ArrayList<Node> rootblock = new ArrayList<>();
	// public static ArrayList<ArrayList<Node>> allblock = new ArrayList<>();

	public Tree(ArrayList<String> address, ArrayList<Integer> value) {
		node_list = new ArrayList<>();
		rootnode = new Node();
		for (int i = 0; i < address.size(); i++) {
			Node node = new Node(address.get(i), value.get(i));
			node.create_hash();
			node_list.add(node);
		}
		rootnode.setHash(get_root(node_list));

	}

	public Node get_rootNode() {
		return rootnode;
	}

	public ArrayList<Node> get_list() {
		return node_list;
	}

	// Get hash from left and right and store all final values in an array
	private static ArrayList<Node> get_hash(ArrayList<Node> mk_arr) {
		ArrayList<Node> new_arr = new ArrayList<>();
		for (int i = 0; i < mk_arr.size(); i++) {
			// Store the left value at first
			String left_hash = mk_arr.get(i).getHash();
			// Move i to the next index
			i++;
			String right_hash = "";
			if (i < mk_arr.size()) {
				right_hash = mk_arr.get(i).getHash();
			}
			Node temp = new Node();
			temp.create_hash(right_hash, left_hash);
			new_arr.add(temp);
		}
		return new_arr;
	}

	// Figure out the root
	private static String get_root(ArrayList<Node> mk_arr) {
		ArrayList<Node> arr = new ArrayList<>();
		arr = get_hash(mk_arr);
		while (arr.size() != 1) {
			arr = get_hash(arr);
		}
		return arr.get(0).getHash();
	}
}