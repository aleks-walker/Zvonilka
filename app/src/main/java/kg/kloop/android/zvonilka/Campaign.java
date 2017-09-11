package kg.kloop.android.zvonilka;

/**
 * Created by alexwalker on 08.09.17.
 */

public class Campaign {
    String title;
    String description;

    public Campaign() {
    }

    public Campaign(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
