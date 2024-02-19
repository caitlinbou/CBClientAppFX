package Model;

public class Count {
    private String type;
    private String month;
    private int count;

    public Count(String type, String month, int count){
        this.type = type;
        this.month = month;
        this.count = count;
    }
    public String toString() {
        return this.type;
    }
}
