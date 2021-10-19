import java.security.MessageDigest;
import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> blocks;
    private ArrayList<String> results;
    
    public ArrayList<String> get_Res(){
    	return this.results;
    }
    
    public  Blockchain (ArrayList<Block> blocks){
        this.blocks = blocks;
        this.results = new ArrayList<>();
    }

    public boolean validate() {
        if (blocks.size() == 0) {
            return true;
        }

        for (int i = 0; i < blocks.size(); i++) {
            Block thisBlock = blocks.get(i);
            if (!thisBlock.previoushash.equals("0")) {
                Block preBlock = blocks.get(i + 1);
                String message = preBlock.previoushash + preBlock.hashRoot + preBlock.timestamp + preBlock.nonce
                        + preBlock.target;
                MessageDigest md;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    md.update(message.getBytes("UTF-8"));
                    String encode = Block.byte2Hex(md.digest());
                    if (!encode.equals(thisBlock.previoushash)) {
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Either no such algorithm or no supported encoding");
                    System.exit(0);
                }
            }
            if (!thisBlock.validate()) {
                return false;
            }
        }
        return true;
    }

    public boolean balance(String address){
        int index1 = -1;
        int index2 = -1;
        for(int i=0;i<blocks.size();i++){
            for(int j=0;j<blocks.get(i).getCT().node_list.size();j++){
                if(address.equals(blocks.get(i).getCT().node_list.get(j).getAddress())){
                    index1 = i;
                    index2 = j;
                    break;
                }
            }
            if(index1>=0){
                break;
            }
        }
        if(index1<0){
            return false;
        }
        else{
            ArrayList<String> hashes1 = new ArrayList<String>();
            ArrayList<String> hashes2 = new ArrayList<String>();
            String message = "";
            MessageDigest md;
            String encode = "";
            for(int i=0;i<blocks.get(index1).getCT().node_list.size();i++){
                message = blocks.get(index1).getCT().node_list.get(i).getAddress() + blocks.get(index1).getCT().node_list.get(i).getValue();
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    md.update(message.getBytes("UTF-8"));
                    encode = byte2Hex(md.digest());
                } catch (Exception e) {
                    System.out.println("Either no such algorithm or no supported encoding");
                    System.exit(0);
                }
                hashes1.add(encode);
                // results.add(encode);
            }
            results.add(hashes1.get(index2));
            while(hashes1.size()>1){
            	hashes2 = new ArrayList<String>();
                for(int i=0;i<hashes1.size();i++){
                    hashes2.add(hashes1.get(i));
                }
                hashes1 = new ArrayList<String>();
                for(int i=0;i<hashes2.size()-1;i+=2){
                    message = hashes2.get(i)+hashes2.get(i+1);
                    try {
                        md = MessageDigest.getInstance("SHA-256");
                        md.update(message.getBytes("UTF-8"));
                        encode = byte2Hex(md.digest());
                    } catch (Exception e) {
                        System.out.println("Either no such algorithm or no supported encoding");
                        System.exit(0);
                    }
                    hashes1.add(encode);
                    if(i==index2){
                        results.add(hashes2.get(i+1));
                        results.add(hashes1.get(i/2));
                    }
                    else if(i+1==index2){
                        results.add(hashes2.get(i));
                        results.add(hashes1.get(i/2));
                    }
                }
                if(hashes2.size()%2!=0){
                    hashes1.add(hashes2.get(hashes2.size()-1));
                }
                index2 = index2/2;
            }
            results.add("previoushash: "+blocks.get(index1).previoushash+"\nhashRoot: "+blocks.get(index1).hashRoot+"\nTimeStamp: "+blocks.get(index1).timestamp+
            "\nnonce: "+blocks.get(index1).nonce+"\ntarget: "+blocks.get(index1).target);
            for(int i=index1;i>=0;i--){
                message = blocks.get(i).previoushash+blocks.get(i).hashRoot+blocks.get(i).timestamp+blocks.get(i).nonce+blocks.get(i).target;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    md.update(message.getBytes("UTF-8"));
                    encode = byte2Hex(md.digest());
                } catch (Exception e) {
                    System.out.println("Either no such algorithm or no supported encoding");
                    System.exit(0);
                }
                results.add(encode);
            }
            
        }
        return true;
    }

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
