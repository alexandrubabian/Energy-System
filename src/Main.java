import actions.Action;
import fileio.Distributor;
import fileio.Input;
import fileio.InputLoader;
import write.Writer;

public final class Main {

    private Main() {

    }
    /**
     * Main method
     */
    public static void main(final String[] args) throws Exception {
        String inputFile = args[0];
        String outputFile = args[1];
        InputLoader inputLoader = new InputLoader(inputFile);
        Input input = inputLoader.readData();
        Action action = new Action(input);
        //initialdata
        Distributor bestDistributor = action.determineBestDistributor();
        action.receiveSalary();
        action.chooseContract(bestDistributor);
        action.distributorPay();
        action.pay();
        action.distributorBankrupt();
        for (int i = 0; i < input.getMonthlyUpdates().size(); i++) {
            action.decreaseConstractMonths();
            action.introduceNewMonthCustomers(i);
            action.introduceMonthCostChanges(i);
            bestDistributor = action.determineBestDistributor();
            action.cleanUpExpiredContracts();
            action.receiveSalary();
            action.chooseContract(bestDistributor);
            action.distributorPay();
            action.pay();
            action.distributorBankrupt();
        }
        action.decreaseConstractMonths();
        Writer writer = new Writer(outputFile);
        writer.writeFile(input);
    }

}
