public class Data {

    String macroName;
    int posParams;
    int keyParams;
    int mdtp;
    int kpdtp;


    public Data(String macroName, int posParams, int keyParams, int mdtp){
        this.keyParams = keyParams;
        this.mdtp = mdtp;
        this.macroName = macroName;
        this.posParams = posParams;
    }
}


