package Model;
/**
 * This describes the "Division" class with getters and setters for all class variables.
 */
public class Division {

    private int divId;
    private String divName;
    private int countryId;
    /**
     * This allows public access of the "Division" class to create an instance of a Division Object.
     */
    public Division(int divId, String divName, int countryId) {
        this.divId = divId;
        this.divName = divName;
        this.countryId = countryId;
    }


    public int getDivId() {
        return divId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String toString()  {
        return this.divName;
    }
}
