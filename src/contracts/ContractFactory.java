package contracts;

import fileio.Common;
import fileio.Consumer;
import fileio.Distributor;

public class ContractFactory {

    private static ContractFactory instance;

    private ContractFactory() {
    }

    public static ContractFactory getInstance() {
        if (instance == null) {
            instance = new ContractFactory();
        }
        return instance;
    }

    public Contract createContract(String contractType, int price, int remainedContractMonths,
                                   int id, Common common) {
        if (contractType.equals("consumer")) {
            return new ConsumerContract((Distributor) common, price, remainedContractMonths);
        } else if (contractType.equals("distributor")) {
            return new DistribContract(id, price, remainedContractMonths, (Consumer) common);
        }
        return null;
    }
}
