package kg.kloop.android.zvonilka.objects;

import java.util.Map;

/**
 * Created by alexwalker on 12.09.17.
 */

public class Client {

    private String id;
    private String name;
    private String phoneNumber;
    private int category;
    private Map<String, String> properties;

    public Client() {
    }

    public Client(String id, String name, String phoneNumber, int category, Map<String, String> properties) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.properties = properties;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
