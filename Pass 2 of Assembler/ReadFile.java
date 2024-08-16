import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class ReadFile {
    public ArrayList<String> readCode(){
        ArrayList<String> instructs = new ArrayList<>();
        try {
            File myObj = new File("/home/pict/IdeaProjects/31174_passtwo/src/lll_ic.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                instructs.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return instructs;
    }

    public ArrayList<String> readSymbolTable(){
        ArrayList<String> instructs = new ArrayList<>();
        try {
            File myObj = new File("/home/pict/IdeaProjects/31174_passtwo/src/st.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                instructs.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return instructs;
    }

    public ArrayList<String> readLiteralTable(){
        ArrayList<String> instructs = new ArrayList<>();
        try {
            File myObj = new File("/home/pict/IdeaProjects/31174_passtwo/src/lt.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                instructs.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return instructs;
    }
}