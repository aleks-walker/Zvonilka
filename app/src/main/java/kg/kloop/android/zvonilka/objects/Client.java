package kg.kloop.android.zvonilka.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexwalker on 12.09.17.
 */

public class Client implements Parcelable {

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
    private ArrayList<Call> callArrayList;

    public Client() {
    }

    public Client(String id, String name, String city, String salon, String position, String phoneNumber, String email, String otherInfo, String toDoInfo, int category, HashMap<String, Integer> interests, ArrayList<Call> callArrayList) {
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
        this.callArrayList = callArrayList;
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

    public ArrayList<Call> getCallArrayList() {
        return callArrayList;
    }

    public void setCallArrayList(ArrayList<Call> callArrayList) {
        this.callArrayList = callArrayList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.city);
        dest.writeString(this.salon);
        dest.writeString(this.position);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeString(this.otherInfo);
        dest.writeString(this.toDoInfo);
        dest.writeInt(this.category);
        dest.writeSerializable(this.interests);
        dest.writeList(this.callArrayList);
    }

    protected Client(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.city = in.readString();
        this.salon = in.readString();
        this.position = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.otherInfo = in.readString();
        this.toDoInfo = in.readString();
        this.category = in.readInt();
        this.interests = (HashMap<String, Integer>) in.readSerializable();
        this.callArrayList = new ArrayList<Call>();
        in.readList(this.callArrayList, Call.class.getClassLoader());
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
