package kg.kloop.android.zvonilka.objects;

/**
 * Created by alexwalker on 23.09.17.
 */

public class Call {
    private String id;
    private Client client;
    private String type;
    private String description;
    private String result;

    public Call() {
    }

    public Call(String id, Client client, String type, String description, String result) {
        this.id = id;
        this.client = client;
        this.type = type;
        this.description = description;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
