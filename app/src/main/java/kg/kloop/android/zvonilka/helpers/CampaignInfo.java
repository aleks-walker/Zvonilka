package kg.kloop.android.zvonilka.helpers;

/**
 * Created by alexwalker on 18.10.2017.
 */

public class CampaignInfo {

    private static String currentCampaignId;

    public CampaignInfo() {
    }


    public static void setCurrentCampaignId(String currentCampaignId) {
        CampaignInfo.currentCampaignId = currentCampaignId;
    }

    public static String getCurrentCampaignId() {
        return currentCampaignId;
    }
}
