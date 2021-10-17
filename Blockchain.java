import java.security.MessageDigest;
import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> blocks;

    public Blockchain(ArrayList<Block> blocks) {
        this.blocks = blocks;
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
}
