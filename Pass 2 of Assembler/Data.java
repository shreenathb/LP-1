import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Data {
  //  Map<String, String> opcode = new HashMap<>();
    HashSet<String> setAD = new HashSet<>();
    HashSet<String> setIS = new HashSet<>();
    HashSet<String> setDL = new HashSet<>();
  //  Map<String, String> mapReg = new HashMap<>();

    public Data(){


        setAD.add("START");
        setAD.add("END");
        setAD.add("ORIGIN");
        setAD.add("EQU");
        setAD.add("LTORG");


        setIS.add("STOP");
        setIS.add("ADD");
        setIS.add("SUB");
        setIS.add("MULT");
        setIS.add("MOVER");
        setIS.add("MOVEM");
        setIS.add("COMP");
        setIS.add("BC");
        setIS.add("DIV");
        setIS.add("READ");
        setIS.add("PRINT");

        setDL.add("DC");
        setDL.add("DS");


    }
}
