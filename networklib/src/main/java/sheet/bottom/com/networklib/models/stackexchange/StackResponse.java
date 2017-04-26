package sheet.bottom.com.networklib.models.stackexchange;

/**
 * Created by labattula on 22/09/16.
 */

public class StackResponse {

    StackItem[] items;

    boolean has_more;
    int quota_max;
    int quota_remaining;

    public StackItem[] getItems() {
        return items;
    }

    public void setItems(StackItem[] items) {
        this.items = items;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public int getQuota_max() {
        return quota_max;
    }

    public void setQuota_max(int quota_max) {
        this.quota_max = quota_max;
    }

    public int getQuota_remaining() {
        return quota_remaining;
    }

    public void setQuota_remaining(int quota_remaining) {
        this.quota_remaining = quota_remaining;
    }
}
