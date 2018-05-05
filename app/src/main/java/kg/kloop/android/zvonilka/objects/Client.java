package kg.kloop.android.zvonilka.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexwalker on 12.09.17.
 */

public class Client {

    private String id;
    private String name;
    private String city;
    private String salon;
    private String position;
    private String phoneNumber;
    private String email;
    private String otherInfo;
    private String toDoInfo;
    private int category;
    private HashMap<String, Integer> interests;

    public Client() {
    }

    public Client(String id, String name, String city, String salon, String position, String phoneNumber, String email, String otherInfo, String toDoInfo, int category, HashMap<String, Integer> interests) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.salon = salon;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.otherInfo = otherInfo;
        this.toDoInfo = toDoInfo;
        this.category = category;
        this.interests = interests;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getToDoInfo() {
        return toDoInfo;
    }

    public void setToDoInfo(String toDoInfo) {
        this.toDoInfo = toDoInfo;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public HashMap<String, Integer> getInterests() {
        return interests;
    }

    public void setInterests(HashMap<String, Integer> interests) {
        this.interests = interests;
    }
}
