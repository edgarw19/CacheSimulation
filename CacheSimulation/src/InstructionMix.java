import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Edgar on 1/8/2015.
 */
public class InstructionMix {

    public static void addToSet(String[] toAdd, Set<String> thisSet) {
        for (int i = 0; i < toAdd.length; i++) {
            thisSet.add(toAdd[i]);
        }
    }

    public static int checkOpCode(String[] opCodeCategory, HashMap<String, Integer> allOpCodes){
        int total = 0;
        for (int i = 0; i < opCodeCategory.length; i++){
            String key = opCodeCategory[i];
            if (allOpCodes.containsKey(key)){
                int occurrences = allOpCodes.get(key);
                total += occurrences;
                System.out.println(key + ": " + occurrences);
            }
        }
        return total;
    }

    public static void calculateInstructionMix(String input) {
        boolean isDynamic = false;
        String pattern = "\\w++(\\-)*\\w*";
        Pattern regexpMatch = Pattern.compile(pattern);
        Matcher matches;
        Set<String> validOpcode = new HashSet<String>();
        HashMap<String, Integer> opcodes = new HashMap<String, Integer>();
        String[] arithmeticOpcodes = {"ADC", "ADDSD", "ADD", "SBB", "SUB", "IMUL", "MUL", "DIV", "IDIV"};
        String[] logicalOpcodes = {"AND", "OR", "XOR", "NOT"};
        String[] branchOpcodes = {"JCC", "JMP", "JLE"};
        String[] dataOpcodes = {"mem-read", "mem-write"};
        addToSet(arithmeticOpcodes, validOpcode);
        addToSet(logicalOpcodes, validOpcode);
        addToSet(branchOpcodes, validOpcode);
        addToSet(dataOpcodes, validOpcode);
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line;

            while ((line = br.readLine()) != null) {
                matches = regexpMatch.matcher(line);
                if (matches.find()) {
                    if (matches.group(0).equals("dynamic-counts")) {
                        line = br.readLine();
                        line = br.readLine();
                        line = br.readLine();
                        isDynamic = true;
                    } else if (isDynamic) {
                        String total = "";
                        while (matches.find()) {
                            total += matches.group() + " ";
                        }
                        if (total.length() > 0) {
                            String[] parts = total.split(" ");
                            String opCode = parts[0];
                            int occurrences;
                            if (opCode.equals("mem-read") || opCode.equals("mem-write")) {
                                occurrences = Integer.parseInt(parts[2]);
                            } else {
                                occurrences = Integer.parseInt(parts[1]);
                            }
                            if (validOpcode.contains(opCode)) {
                                int existing = 0;
                                if (opcodes.containsKey(opCode)) {
                                    existing = opcodes.get(opCode);

                                }
                                opcodes.put(opCode, existing + occurrences);
                            }
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ARITHMETIC OPCODES");
        int arithmetic = checkOpCode(arithmeticOpcodes, opcodes);
        System.out.println("TOTAL: " + arithmetic);
        System.out.println();

        System.out.println("LOGICAL OPCODES");
        int logical = checkOpCode(logicalOpcodes, opcodes);
        System.out.println("TOTAL: " + logical );
        System.out.println();

        System.out.println("DATA OPCODES");
        int data = checkOpCode(dataOpcodes, opcodes);
        System.out.println("TOTAL: " + data);
        System.out.println();

        System.out.println("BRANCHING OPCODES");
        int branching = checkOpCode(branchOpcodes, opcodes);
        System.out.println("TOTAL: " + branching);
        System.out.println();

        int totalInstructions = arithmetic + logical + data + branching;
        System.out.println("TOTAL RELEVANT INSTRUCTIONS: " + totalInstructions);
        System.out.println("ARITHMETIC FRACTION: " + (double)arithmetic/totalInstructions);
        System.out.println("LOGICAL FRACTION: " + (double)logical/totalInstructions);
        System.out.println("BRANCH FRACTION: " + (double)branching/totalInstructions);
        System.out.println("DATA FRACTION: " + (double)data/totalInstructions);



    }

    public static void main(String[] args) {
        InstructionMix.calculateInstructionMix("dataOptimized.txt");
        System.out.println("DONE");
    }
}
