/**
 * Created by Edgar on 12/10/2014.
 */

public class BlockSet{
    private int numberOfBlocks;
    private int blockSize;
    private CacheBlock[] blocks;

    public BlockSet(int numberOfBlocks, int blockSize){
        this.numberOfBlocks = numberOfBlocks;
        blocks = new CacheBlock[numberOfBlocks];
        this.blockSize = blockSize;
        for (int i = 0; i < blocks.length; i++){
            blocks[i] = new CacheBlock(Integer.MIN_VALUE, blockSize);
        }
    }

    public void replaceBlock(long address){
        long minimum = Integer.MAX_VALUE;
        int counter = 0;
        for (int i = 0; i < numberOfBlocks; i++){
            if (!blocks[i].isValid()){
                counter = i;
                break;
            }
            long curBlockTime = blocks[i].getLRU();
            if (curBlockTime < minimum){
                minimum = curBlockTime;
                counter = i;
            }
        }
        blocks[counter].update(address);
    }

    public boolean update(long tag){
        for (int i = 0; i < numberOfBlocks; i++){
            if (blocks[i].find(tag)){
                return true;
            }
        }
        replaceBlock(tag);
        return false;
    }





}
