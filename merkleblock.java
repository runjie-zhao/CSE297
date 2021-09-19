import java.security.*;

public class merkleblock{
	String address;
	int value;
	String hash;
	public merkleblock() {
		return;
	}
	
	//Initialize the merkleblock
	public merkleblock(String address, int value) {
		this.address = address;
		this.value = value;
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