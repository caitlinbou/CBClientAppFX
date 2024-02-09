package Model;

public class Customer {
    private int custId;
    private String custName;
    private String custAddress;
    private String custPostal;
    private String country;
    private String custPhone;
    private int divId;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Customer(int custId, String custName, String custAddress, String custPostal, String country, String custPhone, int divId) {
        this.custId = custId;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostal = custPostal;
        this.country =country;
        this.custPhone = custPhone;
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
