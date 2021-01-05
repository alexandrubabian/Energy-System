import actions.Action;
import fileio.Distributor;
import fileio.Input;
import fileio.InputLoader;
import write.Writer;

import java.util.ArrayList;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        String inputFile = args[0];
        String outputFile = args[1];
        //String inputFile = "/home/andu/Desktop/temaPOO/checker/resources/in/complex_4.json";
        //String outputFile = "/home/andu/Desktop/temaPOO/fisier.out";
        InputLoader inputLoader = new InputLoader(inputFile);
        ArrayList<Distributor> distributorsToChange;
        Input input = inputLoader.readData();
        Action action = new Action(input);
        //initialdata
        action.chooseFirstProducers();
        Distributor bestDistributor = action.determineBestDistributor();
        action.receiveSalary();
        action.chooseContract(bestDistributor);
        action.distributorPay();
        action.pay();
        action.distributorBankrupt();
        for (int i = 0; i < input.getMonthlyUpdates().size(); i++) {
            action.decreaseConstractMonths();
            action.introduceNewMonthCustomers(i);
            action.introduceMonthDistributorChanges(i);
            bestDistributor = action.determineBestDistributor();
            action.cleanUpExpiredContracts();
            action.receiveSalary();
            action.chooseContract(bestDistributor);
            action.distributorPay();
            action.pay();
            action.distributorBankrupt();
            distributorsToChange = action.introduceMonthProducerChanges(i);
            action.changeDistributors(distributorsToChange);
            action.setMonthlyStats(i);
        }
        action.decreaseConstractMonths();
        Writer writer = new Writer(outputFile);
        writer.writeFile(input);
    }

}
