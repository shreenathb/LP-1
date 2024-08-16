import java.util.*;

public class ProcessInstructs {
    ArrayList<String> instructs;
    int lc = 0;
    Map<String, String> opcode ;
    Map<String, String> mapReg;
    ArrayList<ArrayList<String>> ic;
    ArrayList<Integer>lc_array;
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
        lc_array = new ArrayList<>();
    }

    public void processIndividualLine(){
        Boolean flag = false;
        for(String instruct : instructs){
            ArrayList<String> arr = new ArrayList<>();
            String[] words = instruct.split("[,\\s]+");



            Boolean ad = false;
             for(String word : words){
                 if(word.equals("+")){
                     continue;
                 }
                 if(ad){
                     ad = false;
                     continue;
                 }
                 if(opcode.containsKey(word)){
                     // mnemonic

                     if(setAD.contains(word)){

                        if(word.equals("START")){
                            adStart(words);
                            arr.add(opcode.get(words[0]));
                            arr.add("C "+String.valueOf(lc));
                            flag = true;
                        }else if(word.equals("END")){
                            arr.add(opcode.get(word));
                            ic.add(arr);
                            adEnd();
                            flag = true;
                        }else if(word.equals("LTORG")){
                            arr.add(opcode.get(word));
                            ic.add(arr);
                            adLTORG();
                            flag = true;
                        }else if(word.equals("EQU")){
                            int addr = symbolTable.get(words[0]);
                            symbolTable.put(words[0], addr);
                            symbolTableIndex.put(words[0],symbolIndex);
                            symbolIndex++;
                            arr.add(opcode.get(word));
                            ic.add(arr);
                            ad = true;
                            flag = true;
                        }else{
                            // origin
                            lc = symbolTable.get(words[1]) + Integer.parseInt(words[words.length-1]);
                            arr.add(opcode.get(word));
                            arr.add(String.format("S" + " " +symbolTableIndex.get(words[1])+" + %d", Integer.parseInt(words[words.length-1])));
                            ic.add(arr);
                            ad = true;
                            flag = true;
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
                         if(symbolTable.containsKey(word)){
                             continue;
                         }
                         symbolTable.put(word,lc);
                         symbolTableIndex.put(word,symbolIndex);
                         //arr.add(String.format("S %d",symbolIndex));
                         symbolIndex++;

                     }else if(word.charAt(0)=='='){
                         // literal
                         if(literalTable.containsKey(word)){
                             continue;
                         }
                         literalTable.put(word,-1);
                         literalTableIndex.put(word,literalIndex);
                         arr.add(String.format("L %d",literalIndex));
                         literalIndex++;
                     }else{
                         // symbol
                         //arr.add(String.format("C %s",word));
                         if(!onlyDigits(word)){
                             if(symbolTable.containsKey(word)){
                                 continue;
                             }
                             symbolTable.put(word,-1);
                             symbolTableIndex.put(word,symbolIndex);
                             arr.add(String.format("S %d",symbolIndex));
                             symbolIndex++;

                         }

                     }

                 }

             }



             if(flag){
                 System.out.println("-x-" + " " + arr);
                 flag = false;
             }else{
                 System.out.println(lc  + " " + arr);
                 lc++;
             }
             ic.add(arr);
             //System.out.println(arr);


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

    public void adLTORG(){
        for (Map.Entry<String, Integer> entry : literalTable.entrySet()) {
            ArrayList<String> arr = new ArrayList<>();
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value == -1){
                arr.add("DL 01");
                literalTable.put(key, lc);
                System.out.println(lc +" "+arr);
                lc++;
                ic.add(arr);
            }
        }
    }


    public boolean onlyDigits(String str){
        for(int i=0;i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public void adEnd(){
        for (Map.Entry<String, Integer> entry : literalTable.entrySet()) {
            ArrayList<String> arr = new ArrayList<>();
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value == -1){
                arr.add("DC 01");
                arr.add(String.format("C %s", key.charAt(2)));
                literalTable.put(key, lc);
                lc++;
                ic.add(arr);
            }
        }
    }

    public void showSymbolTable(){
        System.out.println("Symbol Table");
        Iterator hmIterator = symbolTable.entrySet().iterator();
        while (hmIterator.hasNext()) {

            Map.Entry mapElement
                    = (Map.Entry)hmIterator.next();
            int num = (int) mapElement.getValue();

            // Printing mark corresponding to string entries
            System.out.println(mapElement.getKey() + " : "
                    + num);
        }

    }

    public void showLiteralTable(){
        System.out.println("Literal Table");
        Iterator hmIterator = literalTable.entrySet().iterator();
        while (hmIterator.hasNext()) {

            Map.Entry mapElement
                    = (Map.Entry)hmIterator.next();
            int num = (int) mapElement.getValue();

            // Printing mark corresponding to string entries
            System.out.println(mapElement.getKey() + " : "
                    + num);
        }

    }


}
