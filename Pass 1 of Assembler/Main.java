import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ReadFile obj = new ReadFile();
        ArrayList<String> instructs = obj.readCode();
        //System.out.println(instructs);
        ProcessInstructs PI = new ProcessInstructs(instructs);
        PI.processIndividualLine();
        //PI.printIC();
        System.out.println();
        PI.showSymbolTable();
        System.out.println();
        PI.showLiteralTable();
    }
}