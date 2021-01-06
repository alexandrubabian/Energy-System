package strategies;

import fileio.Producer;

import java.util.ArrayList;

public interface Strategy {
    /**
     * The interface method which will return the array of producers depending on the strategy used
     * @param producers array of all the producers in the program
     * @param energyNeededKW how much energy will a distributor need
     */
    ArrayList<Producer> doOperation(ArrayList<Producer> producers, int energyNeededKW);
}
