import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Data {
    Map<String, String> opcode = new HashMap<>();
    HashSet<String> setAD = new HashSet<>();
    HashSet<String> setIS = new HashSet<>();
    HashSet<String> setDL = new HashSet<>();
    Map<String, String> mapReg = new HashMap<>();

    public Data(){
        opcode.put("START","AD 01");
        opcode.put("END","AD 02");
        opcode.put("ORIGIN","AD 03");
        opcode.put("EQU","AD 04");
        opcode.put("LTORG","AD 05");

        setAD.add("START");
        setAD.add("END");
        setAD.add("ORIGIN");
        setAD.add("EQU");
        setAD.add("LTORG");



        opcode.put("STOP","IS 00");
        opcode.put("ADD","IS 01");
        opcode.put("SUB","IS 02");
        opcode.put("MULT","IS 03");
        opcode.put("MOVER","IS 04");
        opcode.put("MOVEM","IS 05");
        opcode.put("COMP","IS 06");
        opcode.put("BC","IS 07");
        opcode.put("DIV","IS 08");
        opcode.put("READ","IS 09");
        opcode.put("PRINT","IS 10");

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

        opcode.put("DC","DL 01");
        opcode.put("DS","DL 02");

        setDL.add("DC");
        setDL.add("DS");

        mapReg.put("AREG","1");
        mapReg.put("BREG","2");
        mapReg.put("CREG","3");
        mapReg.put("DREG","4");
    }
}
