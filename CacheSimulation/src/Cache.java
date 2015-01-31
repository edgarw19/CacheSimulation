/**
 * Created by Edgar on 12/10/2014.
 */

import java.io.*;

public class Cache {
    private int capacity;
    private int blockSize;
    private int numBlocks;
    private int numSets;
    private int indexSize;
    private int blockindexSize;
    private int indexMask;
    BlockSet[] blockSets;
    private int associativity;
    int loads = 0;
    int stores = 0;
    int misses = 0;
    int hits = 0;

    public Cache(int capacity, int blockSize, int associativity) {
        indexMask = 0;
        this.capacity = capacity;
        this.blockSize = blockSize;
        this.associativity = associativity;
        numBlocks = capacity / blockSize;
        numSets = numBlocks / associativity;
        indexSize = (int) Math.ceil((double) Math.log(numSets) / Math.log(2));
        blockSets = new BlockSet[numSets];
        blockindexSize = (int) Math.ceil((double) Math.log(blockSize) / Math.log(2));
        for (int i = 0; i < blockSets.length; i++) {
            blockSets[i] = new BlockSet(associativity, blockSize);
        }
        for (int i = 0; i < indexSize; i++) {
            indexMask = indexMask | 1;
            indexMask = indexMask << 1;
        }
        indexMask = indexMask >> 1;
    }

    public void printStatistics() {
        System.out.println("Total References:" + (loads + stores));
        System.out.println("Loads:" + loads);
        System.out.println("Stores:" + stores);
        System.out.println("Misses:" + misses);
        System.out.println("Hits:" + hits);
    }

    public void simulateCache(String input) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    break;
                }
                char operation = parts[1].charAt(0);
                long address = Long.decode(parts[2]);
                readOrWrite(address, operation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void readOrWrite(long address, char operation) {
        long blockNumber = address / blockSize;
        int setNumber = (int) (blockNumber & indexMask);
        if (operation == 'W') stores++;
        else loads++;
        long tag = address >> (indexSize + blockindexSize);
        if (!(blockSets[setNumber].update(tag))) {
            misses++;
        } else {
            hits++;
        }


    }



    public static int sell(int[] morning, int[] customers, int stale_limit)
    {
        //start from the very end
        //make as much as necessary for morning-customers
        //if morning-customers is negative
        //then keep a running track of the negatives required for the day
        //the next day, morning > customers to satisfy negatives required for the day
        //if there are negatives here, also put them in
        //continue the cycle and make as necessary to make the previous negatives zeroes,
        //BUT CAN ONLY GO BACK 2 days
        int N = morning.length;
        int[] slimonadeDebt = new int[N+stale_limit];
        int totalSold = 0;
        for (int i = N-1; i >= 0; i--)
        {
            int leftOvers = morning[i] - customers[i];
            if (leftOvers > 0) //must be a greater than
            {
                for (int j = i+1; j < i+1+stale_limit; j++){ //i must be i+1+stale
                    if (leftOvers <= 0) break;
                    if (slimonadeDebt[j] > 0){
                        int difference = slimonadeDebt[j] - leftOvers;
                        if (difference > 0){
                            totalSold += leftOvers;
                            slimonadeDebt[j] = difference;
                            leftOvers = -1;
                        }
                        else {
                            totalSold += slimonadeDebt[j]; //must be += rather than +
                            leftOvers = -difference;
                            slimonadeDebt[j] = 0;
                        }
                    }
                }
            }
            else
            {
                totalSold += morning[i];
                slimonadeDebt[i] = -leftOvers;

            }
        }
        System.out.println("TOTAL SOLD" + totalSold);
        return totalSold;
    }

    public static void sortSkills(String[] score, int x) {
        int N = score.length;
        int[] pos = new int[N];
        char[] chosenSkill = new char[N];
        for (int i = 0; i < N; i++) {
            pos[i] = i;
        }
        for (int i = 0; i < N; i++) {
            chosenSkill[i] = score[i].charAt(x);
        }
        for (int i = 0; i < N; i++) {
            char minChar = chosenSkill[i];
            int minInt = 0;
            for (int j = i + 1; j < N; j++) {
                if (chosenSkill[j] < minChar) {
                    minInt = j;
                    minChar = chosenSkill[j];
                }
            }
            char tempChar = minChar;
            int tempInt = minInt;
            chosenSkill[tempInt] = chosenSkill[i];
            pos[tempInt] = pos[i];
            pos[i] = minInt;
            chosenSkill[i] = minChar;
        }
        for (int i = 0; i < N; i++) {
            System.out.print(pos[i] + " ");
        }
    }


    public static void main(String[] args) {
        String[] test = {"ACB", "BAC", "CBA"};
        Cache.sortSkills(test, 1);
        int[] morning =
                {1, 2, 3, 4, 5};
        int[] customers =
                {5, 5, 5, 5, 5};
        int stale =
                5;

        Cache.sell(morning, customers, stale);
        Cache newCache = new Cache(32, 1, 2);
        newCache.simulateCache("regtrace.txt");
        newCache.printStatistics();

    }
}

