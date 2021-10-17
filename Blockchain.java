import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> blocks;
    private ArrayList<String> results;
    public  Blockchain (ArrayList<Block> blocks){
        this.blocks = blocks;
    }
    
    public boolean validate(){
        return true;
    }

    public boolean balance(String address){
        int index1 = -1;
        int index2 = -1;
        for(int i=0;i<blocks.size();i++){
            for(int j=0;j<blocks.get(i).getCT().node_list.size();j++){
                if(address.equals(blocks.get(i).getCT().node_list.get(j).getAddress)){
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
            for(int i=0;i<blocks.get(index1).getCT().node_list.size()){
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
                results.add(encode);
            }
            results.add(hashes1.get(index2));
            while(hashes1.size()>1){
                for(int i=0;i<hashes1.size();i++){
                    hahses2.add(hashes1.get(i));
                }
                hashes1 = new ArrayList<String>();
                for(int i=0;i<hahses2.size();i+=2){
                    message = hashes2.get(i)+hahses2.get(i+1);
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
                    else if(i==index2+1){
                        results.add(hashes2.get(i));
                        results.add(hashes1.get(i/2));
                    }
                }
                if(hahses2.size()%2!=0){
                    hashes1.add(hashes2.get(hashes2.size()-1));
                }
            }
            results.add("previoushash: "+blocks.get(index1).previoushash+"\nhashRoot: "+blocks.get(index1).hashRoot+"\nTimeStamp: "+blocks.get(index1).timestamp+
            "\nnonce: "+blocks.get(index1).nonce+"\ntarget: "+blocks.get(index1).target);
            for(int i=index1-1;i>=0;i--){
                results.add(blocks.get(i).previoushash);
            }
            message = blocks.get(0).previoushash+blocks.get(0).hashRoot+blocks.get(0).timestamp+blocks.get(0).nonce+blocks.get(0).target;
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
        return true;
    }
}
