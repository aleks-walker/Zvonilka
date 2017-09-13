package kg.kloop.android.zvonilka.objects;

/**
 * Created by alexwalker on 12.09.17.
 */

public class Client {

    private String id;
    private String name;
    private String phoneNumber;

    public Client() {
    }

    public Client(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}