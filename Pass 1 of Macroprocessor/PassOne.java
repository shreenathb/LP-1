import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PassOne {
    ArrayList<String> instructs;
    Map<String, Data> mnt = new HashMap<>();
    // Map<String, Integer> pntab = new HashMap<>();
    Map<String,Map<String, Integer>> pntabs = new HashMap<>();
    Map<String, KPDTAB> kpdtab = new HashMap<>();
    Boolean insideMacro;
    Boolean updateMNT;
    int kpdtab_index;
    String current_macro_name;
    int pntab_index;
    int mdt_index;
    ArrayList<String>mdt = new ArrayList<>();
    Map<String, Integer> pntab = new HashMap<>();

    public PassOne(){
        ReadFile obj = new ReadFile();
        instructs = obj.readCode();
        insideMacro = false;
        updateMNT = false;
        kpdtab_index=0;
        pntab_index = 0;
        mdt_index = 0;
    }

    public void processMacro(){
        for(int i=0;i<instructs.size();i++){
            String instruct = instructs.get(i);
            if(insideMacro){
                if(instruct.equals("MEND")){
                    insideMacro = false;
                    mdt.add(instruct);
                    mdt_index++;
                    pntabs.put(current_macro_name, pntab);
                    break;
                }
                String[] arr = instruct.split("[\\s,]+");
                if(updateMNT){
                    updateMNT = false;
                    String macroName = arr[0];
                    current_macro_name = macroName;


                    int count_pos = 0;
                    int count_key = 0;
                    for(int j=1;j<arr.length;j++){
                        String word = arr[j];
                        if(word.contains("=")){
                            count_key++;
                            if(word.charAt(word.length()-1) != '='){
                                String arr2[] = word.split("\\=");
                                KPDTAB ob = new KPDTAB(kpdtab_index++,arr2[1],arr2[0].substring(1));

                                kpdtab.put(ob.keyword_param, ob);
                                pntab.put(ob.keyword_param, pntab_index++);
                            }else{
                                KPDTAB ob = new KPDTAB(kpdtab_index++,"NA",word.substring(1, word.length()-1));
                                kpdtab.put(ob.keyword_param, ob);
                                pntab.put(ob.keyword_param, pntab_index++);
                            }

                        }else{
                            count_pos++;
                            pntab.put(word.substring(1), pntab_index++);
                        }
                    }

                    mnt.put(macroName, new Data(current_macro_name, count_pos, count_key,mdt_index));
                }else{
                    String t="";
                    for(int j=0;j< arr.length;j++){
                        if(arr[j].contains("&")){
                            int ind = pntab.get(arr[j].substring(1));
                            t = t + String.format(" P %d", ind);

                        }else{
                            t = t + " " + arr[j];
                        }
                    }
                    mdt.add(t);
                    mdt_index++;
                }
            }else{
                if(instruct.equals("MACRO")){
                    insideMacro = true;
                    updateMNT = true;

//                    String macroName = instructs.get(i+1).split("[\\s,]+")[0];
//                    mnt.put(macroName, new Data());
                }
            }
        }
    }

    public void displayMDT(){
        for(String s : mdt){
            System.out.println(s);
        }
    }

    public void displaypntab(){
        for (String name: pntab.keySet()) {

            String value = pntab.get(name).toString();
            System.out.println(name + " " + value);
        }
    }
}
