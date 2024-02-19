package Model;
/**
 * This describes the "Countries" class with getters and setters for all class variables.
 */
public class Countries {
    private int id;
    private String name;
    /**
     * This allows public access of the "Countries" class to create an instance of a Countries Object.
     */
    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String toString()  {
        return this.name;
    }
}
