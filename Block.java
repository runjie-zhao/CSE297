import java.security.*;
import java.util.*;

public class Block{
    String previoushash;    
    String hashRoot;
    long timestamp;
    String nonce;
	double target;
    Tree currentT;
    
    public Block() {}
    
    public Block(Tree ct, Block b, double t) {
        currentT = ct;
        String message = b.previoushash+b.hashRoot+b.timestamp+b.nonce+b.target;
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
        previoushash = encode;
        try {
            message = ct.get_rootNode().getHash();
            md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes("UTF-8"));
            encode = byte2Hex(md.digest());
        }catch(Exception e) {
            System.out.println("Either no such algorithm or no supported encoding");
            System.exit(0);
        }
        hashRoot = encode;
		this.timestamp = new Date().getTime();
        target = t;
        try {
            while(true){
                int temp = (int)(Math.random()*Integer.MAX_VALUE);
                message = Integer.toHexString(temp)+ct.get_rootNode().getHash();
                md = MessageDigest.getInstance("SHA-256");
                md.update(message.getBytes("UTF-8"));
                encode = byte2Hex(md.digest());
                if(encode.compareTo(Long.toHexString(0x7FFFFFFF).toUpperCase())<=0){
                    nonce = encode;
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println("Either no such algorithm or no supported encoding");
            System.exit(0);
        }
	}

    public Block(Tree ct, double t) {
        currentT = ct;
        String message = "";
        MessageDigest md;
        String encode="";
        previoushash = "0";
        try {
            message = ct.get_rootNode().getHash();;
            md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes("UTF-8"));
            encode = byte2Hex(md.digest());
        }catch(Exception e) {
            System.out.println("Either no such algorithm or no supported encoding");
            System.exit(0);
        }
        hashRoot = encode;
		this.timestamp = new Date().getTime();
        target = t;
        try {
            while(true){
                int temp = (int)(Math.random()*Integer.MAX_VALUE);
                message = Integer.toHexString(temp)+ct.get_rootNode().getHash();
                md = MessageDigest.getInstance("SHA-256");
                md.update(message.getBytes("UTF-8"));
                encode = byte2Hex(md.digest());
                if(encode.compareTo(Long.toHexString(0x7FFFFFFF).toUpperCase())<=0){
                    nonce = encode;
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println("Either no such algorithm or no supported encoding");
            System.exit(0);
        }
	}

    public String printBlock(boolean detail){
        String result = "";
        //System.out.println("BEGIN BLOCK");
        result = result+"BEGIN BLOCK\n";
        //System.out.println("BEGIN HEADER");
        result = result+"BEGIN HEADER\n";
        //System.out.println("previous block: "+previoushash);
        result = result+"previous block: "+previoushash+"\n";
        //System.out.println("root: "+ hashRoot);
        result = result+"root: "+ hashRoot+"\n";
        //System.out.println("timestamp: "+ timestamp);
        result = result+"timestamp: "+ timestamp+"\n";
        //System.out.println("difficulty target: "+ target);
        result = result+"difficulty target: "+ target+"\n";
        //System.out.println("nonce: "+nonce);
        result = result+"nonce: "+nonce+"\n";
        if(detail){
            for(int i=0;i<currentT.node_list.size();i++){
                //System.out.println(currentT.node_list.get(i).getAddress()+" "+currentT.node_list.get(i).getValue());
                result = result+currentT.node_list.get(i).getAddress()+" "+currentT.node_list.get(i).getValue()+"\n";
            }
        }
        //System.out.println("END BLOCK");
        //System.out.println();
        result = result + "END BLOCK\n";
        return result;
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

    public Tree getCT(){
        return currentT;
    }

    public boolean validate() {
        String true_hashRoot = currentT.get_rootNode().getHash();
        if (!true_hashRoot.equals(hashRoot)){
            return false;
        }

        return true;

    }
}