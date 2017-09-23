package kg.kloop.android.zvonilka.objects;

/**
 * Created by alexwalker on 23.09.17.
 */

public class Call {
    private String id;
    private String phoneNumber;
    private String type;
    private String date;
    private String duration;
    private String description;

    public Call() {
    }

    public Call(String id, String phoneNumber, String type, String date, String duration, String description) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * INCOMING_TYPE == 1 (CallLog.Calls.INCOMING_TYPE)
     * OUTGOING_TYPE == 2 (CallLog.Calls.OUTGOING_TYPE)
     * MISSED_TYPE == 3 (CallLog.Calls.MISSED_TYPE)
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
