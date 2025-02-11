package ca.utoronto.utm.paint;
public class OllamaPaintTOMARK {
    public static void main(String [] args){
        OllamaPaint op = new OllamaPaint(args[0]); 
        for(int i=1;i<=3;i++){
            op.newFile1("PaintFile1_"+i+".txt");
            op.newFile2("PaintFile2_"+i+".txt");
            op.newFile3("PaintFile3_"+i+".txt");
        }
    }
}
