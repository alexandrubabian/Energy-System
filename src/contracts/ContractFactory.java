package contracts;

import fileio.Common;
import fileio.Consumer;
import fileio.Distributor;

public final class ContractFactory {

    private static ContractFactory instance;

    private ContractFactory() {
    }
    /**
     * Singleton method
     * @return the instance of the class
     */
    public static ContractFactory getInstance() {
        if (instance == null) {
            instance = new ContractFactory();
        }
        return instance;
    }
    /**
     * Creates a new contract
     *
     * @param contractType
     * @param common
     * @param id
     * @param price
     * @param remainedContractMonths
     * @return an Genre Enum
     */
    public Contract createContract(final String contractType, final int price,
                                   final int remainedContractMonths, final int id,
                                   final Common common) {
        if (contractType.equals("consumer")) {
            return new ConsumerContract((Distributor) common, price, remainedContractMonths);
        } else if (contractType.equals("distributor")) {
            return new DistribContract(id, price, remainedContractMonths, (Consumer) common);
        }
        return null;
    }
}
