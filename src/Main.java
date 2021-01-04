import actions.Action;
import fileio.Distributor;
import fileio.Input;
import fileio.InputLoader;
import write.Writer;

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
        InputLoader inputLoader = new InputLoader(inputFile);
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
            action.introduceMonthProducerChanges(i);
        }
        action.decreaseConstractMonths();
        Writer writer = new Writer(outputFile);
        writer.writeFile(input);
    }

}
