/**
 * Created by Edgar on 12/10/2014.
 */

public class CacheBlock{
    boolean valid;
    private long lastAccess;
    private long tag;
    private int blockSize;


    public CacheBlock(int newTag, int blocksize){
        this.tag = newTag;
        this.blockSize = blocksize;
        valid = false;
    }

    public boolean isValid(){
        return valid;
    }

    public boolean find(long searchTag){
        boolean isHit = false;
        if (searchTag == tag){
            isHit = true;
        }
        return isHit;
    }

    public long getLRU(){
        return lastAccess;
    }

    public void update(long newTag){
        valid = true;
        tag = newTag;
        lastAccess = System.currentTimeMillis();

    }



}
