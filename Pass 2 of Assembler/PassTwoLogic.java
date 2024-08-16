import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PassTwoLogic {
    int lc;
    ArrayList<String> ic;
    ArrayList<String> st;
    ArrayList<String> lt;
    Map<Integer, Integer> symbolTable = new HashMap<>();
    Map<Integer, Integer> literalTable = new HashMap<>();

    public PassTwoLogic(){
        ReadFile obj = new ReadFile();
        ic = obj.readCode();
        st = obj.readSymbolTable();
        lt = obj.readLiteralTable();

    }

    public void getTables(){
        for(String word : st){
            String[] arr = word.split("\\s+");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            symbolTable.put(a,b);
        }

        for(String word : lt){
            String[] arr = word.split("\\s+");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            literalTable.put(a,b);
        }
    }

    public void getPassTwo(){
        getTables();
        for(String word : ic){
            if(word.charAt(0)=='N'){
                System.out.println("NA");
                continue;
            }
            lc = Integer.parseInt(word.substring(0,2).trim());
            String[] arr = word.split("\\s+");
            System.out.print(lc + " ");

            if(arr[1].charAt(1)=='I'){
                if(arr[1].equals("(IS,00)")){
                    System.out.println("+000000");
                    continue;
                }
                System.out.print("+");
                int count = -1;
                for(String x : arr){
                    if(count == 0){
                        System.out.print(x.substring(4,x.length()-1));
                    }else if(count == 1){
                        System.out.print(x.substring(1,x.length()-1));
                    }else if(count==2){
                        if(x.charAt(1)=='S'){
                            int addr = symbolTable.get(Integer.parseInt(x.substring(3,x.length()-1)));
                            String res = String.format("%03d", Integer.parseInt(Integer.toString(addr)));
                            System.out.print(res+"\n");
                        }else{
                            int addr = literalTable.get(Integer.parseInt(x.substring(3,x.length()-1)));
                            String res = String.format("%03d", Integer.parseInt(Integer.toString(addr)));
                            System.out.print(res+"\n");
                        }
                    }
                    count++;
                }
            }else if(arr[1].equals("(DL,01)")){
                String x = arr[2].substring(3,arr[2].length()-1);
                String res = String.format("%06d", Integer.parseInt(x));
                System.out.print("+"+res+"\n");
            }else{
                String x = arr[2].substring(3,arr[2].length()-1);
                int a = Integer.parseInt(x);
                for(int i=0;i<a;i++){
                    if(i==0){
                        System.out.println("+");
                        continue;
                    }
                    System.out.println(i+lc + " +");
                }
            }
        }
    }
}
