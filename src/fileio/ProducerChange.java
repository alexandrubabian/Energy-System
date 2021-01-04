package fileio;

public class ProducerChange {

    private final int id;

    private final int energyPerDistributor;

    public ProducerChange(int id, int energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }
}
