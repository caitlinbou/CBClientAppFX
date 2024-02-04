package Model;

public class Users {
    private int id;
    private String name;
    private String password;

    public Users(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String getPassword() {return password;}
}
