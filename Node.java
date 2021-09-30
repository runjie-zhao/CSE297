import java.security.*;

public class Node {
	// Only leaf needs address and value
	private String address;
	private int value;
	// compute the hash from left + right or from address + value; It can also be
	// the root hash.
	private String hash;

	public Node() {
		// this.timestamp = new Date().getTime();
		return;
	}

	// Initialize the merkleblock
	public Node(String address, int value) {
		// this.timestamp = new Date().getTime();
		this.address = address;
		this.value = value;
	}

	// Initialize the merkleblock
	public Node(String hash) {
		// this.timestamp = new Date().getTime();
		this.hash = hash;
	}

	// Create the hash based on address and value
	public String create_hash() {
		String message = this.address + this.value;
		MessageDigest md;
		String encode = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes("UTF-8"));
			encode = byte2Hex(md.digest());
		} catch (Exception e) {
			System.out.println("Either no such algorithm or no supported encoding");
			System.exit(0);
		}
		this.hash = encode;
		return encode;
	}

	// Create the hash from left node and right node
	public String create_hash(String right, String left) {
		String message = right + left;
		MessageDigest md;
		String encode = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes("UTF-8"));
			encode = byte2Hex(md.digest());
		} catch (Exception e) {
			System.out.println("Either no such algorithm or no supported encoding");
			System.exit(0);
		}
		this.hash = encode;
		return encode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getAddress() {
		return address;
	}

	public int getValue() {
		return value;
	}

	public String getHash() {
		return hash;
	}

	// Test if the nonce can be used to make a value less than or equal to the
	// specified target.
	public String test(String right, String left) {
		String message = right + left;
		MessageDigest md;
		String encode = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes("UTF-8"));
			encode = byte2Hex(md.digest());
		} catch (Exception e) {
			System.out.println("Either no such algorithm or no supported encoding");
			System.exit(0);
		}
		return encode;
	}

	// Convert byte to hex
	public static String byte2Hex(byte[] bytes) {
		String hex = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < bytes.length; n++) {
			hex = Integer.toHexString(bytes[n] & 0xFF);
			sb.append((hex.length() == 1) ? "0" + hex : hex);
		}
		return sb.toString().trim();
	}

}