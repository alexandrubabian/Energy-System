package strategies;

import fileio.Producer;

import java.util.ArrayList;

public interface Strategy {
    public ArrayList<Producer> doOperation(final ArrayList<Producer> producers,
                                           final int energyNeededKW);
}
