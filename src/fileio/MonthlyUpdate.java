package fileio;

import java.util.List;

public class MonthlyUpdate {

    private final List<Consumer> newConsumers;

    private final List<CostChange> costChanges;

    public MonthlyUpdate(List<Consumer> newConsumers, List<CostChange> costChanges) {
        this.newConsumers = newConsumers;
        this.costChanges = costChanges;
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<CostChange> getCostChanges() {
        return costChanges;
    }

}
