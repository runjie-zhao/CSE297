import java.security.*;
import java.util.*;

public class merkleblock{
	//Header
	String header;
	//Only leaf needs address and value
	String address;
	int value;
	//compute the hash from left + right or from address + value; It can also be the root hash.
	String hash;
	//Timestamp
	long timestamp;
	//compute previous hash and only the root can have the it;
	String previoushash;
	String nonce;
	String target;
	public merkleblock() {
		this.timestamp = new Date().getTime();
		return;
	}
	
	//Initialize the merkleblock
	public merkleblock(String address, int value) {
		this.timestamp = new Date().getTime();
		this.address = address;
		this.value = value;
	}
	
	//Initialize the merkleblock
	public merkleblock(String hash) {
		this.timestamp = new Date().getTime();
		this.hash = hash;
	}
	
	//Create the hash based on address and value
	public String create_hash() {
		String message = this.address + this.value;
		MessageDigest md;
		String encode="";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes("UTF-8"));
			encode = byte2Hex(md.digest());
		}catch(Exception e) {
			System.out.println("Either no such algorithm or no supported encoding");
			System.exit(0);
		}
		this.hash = encode;
		return encode;
	}
	
	//Create the hash from left node and right node
	public String create_hash(String right, String left) {
		String message = right+left;
		MessageDigest md;
		String encode="";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes("UTF-8"));
			encode = byte2Hex(md.digest());
		}catch(Exception e) {
			System.out.println("Either no such algorithm or no supported encoding");
			System.exit(0);
		}
		this.hash = encode;
		return encode;
	}
	
	//Test if the nonce can be used to make a value less than or equal to the specified target.
	public String test(String right, String left) {
		String message = right+left;
		MessageDigest md;
		String encode="";
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(message.getBytes("UTF-8"));
			encode = byte2Hex(md.digest());
		}catch(Exception e) {
			System.out.println("Either no such algorithm or no supported encoding");
			System.exit(0);
		}
		return encode;
	}
	
	//Convert byte to hex
	public String byte2Hex(byte[] bytes) {
		String hex = "";
		StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            hex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((hex.length() == 1) ? "0" + hex : hex);
        }
        return sb.toString().trim();
	}
}