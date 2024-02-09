package Model;

public class Customer {
    private int custId;
    private String custName;
    private String custAddress;
    private String custPostal;

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public int getCustCountryID() {
        return custCountryID;
    }

    public void setCustCountryID(int custCountryID) {
        this.custCountryID = custCountryID;
    }

    public String getCustDivision() {
        return custDivision;
    }

    public void setCustDivision(String custDivision) {
        this.custDivision = custDivision;
    }

    private String custCountry;
    private int custCountryID;
    private String custDivision;
    private String custPhone;
    private int divId;

    public Customer(int custId, String custName, String custAddress, String custPostal, String custCountry, int custCountryID, String custDivision, String custPhone, int divId) {
        this.custId = custId;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostal = custPostal;
        this.custCountry = custCountry;
        this.custCountryID = custCountryID;
        this.custPhone = custPhone;
        this.custDivision = custDivision;
        this.divId = divId;
    }
    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPostal() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public int getDivId() {
        return divId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }

    public String toString()  {
        return this.custName;
    }


}
