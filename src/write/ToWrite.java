package write;

import contracts.DistribContract;
import fileio.Consumer;
import fileio.Distributor;
import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public final class ToWrite {

    private List<ToWriteConsumer> consumers;

    private List<ToWriteDistributor> distributors;

    public ToWrite() {
        this.consumers = new ArrayList<>();
        this.distributors = new ArrayList<>();
    }

    public List<ToWriteConsumer> getConsumers() {
        return consumers;
    }

    public List<ToWriteDistributor> getDistributors() {
        return distributors;
    }

    /**
     * Parse the classes consumer and distributor in classes with specific format to print
     * as JSON
     *
     * @param input
     */
    public void transformToWrite(final Input input) {
        for (Consumer iterator : input.getConsumers()) {
            consumers.add(new ToWriteConsumer(iterator.getId(), iterator.getBankrupt(),
                    iterator.getBuget()));
        }
        ToWriteContracts toWriteContracts;
        for (Distributor iterator : input.getDistributors()) {
            distributors.add(new ToWriteDistributor(iterator.getId(), iterator.getBuget(),
                    iterator.getBankrupt()));
            for (DistribContract contractIterator : iterator.getContracts()) {
                toWriteContracts = new ToWriteContracts(contractIterator.getConsumerId(),
                        contractIterator.getPrice(), contractIterator.getRemainedContractMonths());
                distributors.get(distributors.size() - 1).getContracts().add(toWriteContracts);
            }
        }
    }
}
