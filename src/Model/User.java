package Model;
/**
 * This describes the "User" class with getters and setters for all class variables.
 */
public class User {
    private int id;
    private String name;
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * This allows public access of the "User" class to create an instance of a User Object.
     * @param id takes in an int id
     * @param name takes in a String name
     * @param password takes in a String password
     */
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String getPassword() {return password;}

    public String toString()  {
        return this.name;
    }
}
