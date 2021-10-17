import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> blocks;

    public  Blockchain (ArrayList<Block> blocks){
        this.blocks = blocks;
    }
    
    public boolean validate(){
        return true;
    }
}
