import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pass_two_logic {
    ArrayList<String>instructs;
    Map<String, Data> mnt;
    Map<Integer, String> aptab;
    Map<String, String> kpdtab;
    ArrayList<String> mdt = new ArrayList<>();


    public pass_two_logic(){
        readFile rf = new readFile();
        instructs = rf.readCode("/Users/shreenath/Documents/College Sem V Labs/LP-1/Pass 2 of Macroprocessor/src.txt");

        mdt.add("ADD P-3, P-1");
        mdt.add("MOVEM P-3, P-2");
        mdt.add("PRINT P-1");
        mdt.add("MEND");
        mdt.add("ADD P-3, P-1");
        mdt.add("MOVEM P-3, P-2");
        mdt.add("PRINT P-1");
        mdt.add("MEND");



        mnt = new HashMap<>();
        mnt.put("XYZ",new Data("XYZ", 1,2,0,1 ));
        mnt.put("ABC",new Data("ABC", 2,1,4,1 ));
    }
    public void pass_two(){
        int macro_count = 0;

        for(String instruct : instructs){
            if(instruct.equals("MACRO")){
                macro_count++;
                continue;
            }else if(instruct.equals("MEND")){
                macro_count--;
                continue;
            }else if(macro_count == 0){
                System.out.println(instruct);
            }else{
                continue;
            }

            String[] arr = instruct.split("[\\s,]+");

            if(!mnt.containsKey(arr[0])){
                // not a macro call
                continue;
            }

            // macro call

            // verify call
            int pos_params = mnt.get(arr[0]).posParams;
            int key_params = mnt.get(arr[0]).keyParams;
            int i=1;
            aptab = new HashMap<>();
            int count = 0;
            for(;i<=pos_params;i++){
                if(i >= arr.length){
                    System.out.println("Compilation error: Expected more arguments");
                    return;
                }
                aptab.put(i, arr[i]);
                count++;
            }
            // need to check for default values

            for(int x=0;x<key_params;x++){

                if(arr[x+i].contains("&")) {
                    count++;
                    int ind1 = arr[x + i].indexOf("&");
                    int ind2 = arr[x + i].indexOf("=");

                   // String p = arr[x + i].substring(ind1 + 1, ind2);
                    String val = arr[x + i].substring(ind2 + 1);

                    aptab.put(x+i, val);
                    continue;
                }



                aptab.put(x+i, arr[x+i]);
            }


            //displayaptab();

            int mdtp = mnt.get(arr[0]).mdtp;

            for(int i1=mdtp;i1<mdt.size();i1++){
                String arr1[] = mdt.get(i1).split("[\\s,]+");
                String modified = "";

                if(arr1[0].equals("MEND")){
                    break;
                }

                for(String temp : arr1){
                    //System.out.println(temp);
                    if(temp.charAt(0) == 'P' && temp.charAt(1) == '-'){
                        int param = Integer.parseInt(temp.substring(2));
                        modified += aptab.get(param) + " ";
                    }else{
                        modified += temp+" ";
                    }
                }

                System.out.println("+ "+modified);
            }


        }
    }

    public void displayaptab(){
        System.out.println("APTAB");
        int ind = 0;
        for (Integer p: aptab.keySet()) {
            System.out.println(p + " " + aptab.get(p));
        }
        System.out.println();
    }
}
