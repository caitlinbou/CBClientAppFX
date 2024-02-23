package Model;
/**
 * This describes the "Count" class with getters and setters for all class variables.
 */
public class Count {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String type;
    private String month;
    private int count;
    /**
     * This allows public access of the "Count" class to create an instance of a Count Object.
     * @param type takes in String type
     * @param month takes in String month
     * @param count takes in int count
     */
    public Count(String type, String month, int count){
        this.type = type;
        this.month = month;
        this.count = count;
    }
}
