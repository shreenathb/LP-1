import java.util.*;

public class ProcessInstructs {
    ArrayList<String> instructs;
    int lc = 0;
    Map<String, String> opcode ;
    Map<String, String> mapReg;
    ArrayList<ArrayList<String>> ic;
    HashSet<String> setAD;
    HashSet<String> setIS;
    HashSet<String> setDL;

    Map<String, Integer> symbolTable = new HashMap<>();
    Map<String, Integer> symbolTableIndex = new HashMap<>();
    Map<String, Integer> literalTable = new HashMap<>();
    Map<String, Integer> literalTableIndex = new HashMap<>();

    int symbolIndex = 1;
    int literalIndex = 1;

    public ProcessInstructs(ArrayList<String> instructs){
        this.instructs = instructs;
        Data obj = new Data();
        opcode = obj.opcode;
        this.setAD = obj.setAD;
        this.setDL = obj.setDL;
        this.setIS = obj.setIS;
        this.mapReg = obj.mapReg;
        ic = new ArrayList<>();
    }

    public void processIndividualLine(){
        for(String instruct : instructs){
            ArrayList<String> arr = new ArrayList<>();
            String[] words = instruct.split("[,\\s]+");

            Arrays.stream(words)
                    .forEach(System.out::println);

            Boolean flag = false;
             for(String word : words){
                 if(opcode.containsKey(word)){
                     // mnemonic

                     if(setAD.contains(word)){
                         flag = true;
                        if(word.equals("START")){
                            adStart(words);
                            arr.add(opcode.get(words[0]));
                            arr.add(String.valueOf(lc));

                        }else if(word.equals("END")){
                            arr.add(opcode.get(word));
                            for (Map.Entry<String, Integer> entry : literalTable.entrySet()) {
                                String key = entry.getKey();
                                Integer value = entry.getValue();
                                if(value == -1){
                                    arr.add("DL 01");
                                    arr.add(String.format("C %s", key.charAt(2)));
                                    literalTable.put(key, lc);
                                    lc++;
                                }
                            }
                        }
                     }else if(setDL.contains(word)){
                         if(words[1].equals("DS")){
                             String symbol = words[0];
                             symbolTable.put(symbol, lc);
                             arr.add("DL 02");
                             arr.add(String.format("C %d",Integer.parseInt(words[2])));
                             lc += Integer.parseInt(words[2]);
                         }
                     }else{
                         // IS
                         arr.add(opcode.get(word));
                     }
                 }else{
                     // contstants, literals, registers and labels
                     if(mapReg.containsKey(word)){
                         arr.add(mapReg.get(word));
                     }else if(word.equals(words[0])){
                         // label
                         symbolTable.put(word,lc);
                         symbolTableIndex.put(word,symbolIndex);
                         arr.add(String.format("S %d",symbolIndex));
                         symbolIndex++;

                     }else if(word.charAt(0)=='='){
                         // literal
                         literalTable.put(word,-1);
                         literalTableIndex.put(word,literalIndex);
                         arr.add(String.format("L %d",literalIndex));
                         literalIndex++;
                     }else{
                         // constant
                         //arr.add(String.format("C %s",word));
                         symbolTable.put(word,-1);
                         symbolTableIndex.put(word,symbolIndex);
                         arr.add(String.format("S %d",symbolIndex));
                         symbolIndex++;
                     }

                 }
             }

             if(flag){
                 flag = false;
             }else{
                 lc++;
             }
             ic.add(arr);
        }
    }

    public void printIC(){
        System.out.println(ic);
    }

    public void adStart(String[] words){
        if(words.length==1){
            lc = 0;
        }else{
            lc = Integer.parseInt(words[1]);
        }
    }


}
