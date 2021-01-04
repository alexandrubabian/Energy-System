package strategies;

import fileio.Producer;

import java.util.ArrayList;

public interface Strategy {
    /*
    returneaza lista de producatori de la care distribuitorul care a apelat aceasta metoda,
    isi va lua curent
    */
    public ArrayList<Producer> doOperation(ArrayList<Producer> producers, int energyNeededKW);
}
