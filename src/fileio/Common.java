package fileio;

public abstract class Common {
    private final int id;

    public Common(final int id) {
        this.id = id;
    }
    /**
     * Getter for the id
     * @return the id of the object
     */
    public int getId() {
        return id;
    }

}
