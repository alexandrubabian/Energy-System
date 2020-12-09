import fileio.Input;
import fileio.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputFile = args[0];
        String outputFile = args[1];
        InputLoader inputLoader = new InputLoader(inputFile);
        Input input = inputLoader.readData();
    }
}
