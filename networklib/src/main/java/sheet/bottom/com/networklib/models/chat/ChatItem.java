package sheet.bottom.com.networklib.models.chat;

/**
 * Created by labattula on 07/04/17.
 */

public class ChatItem {
    String owner;
    String chatText;
    String chatTime;
    boolean isAiReply;

    public boolean isAiReply() {
        return isAiReply;
    }

    public void setAiReply(boolean aiReply) {
        isAiReply = aiReply;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }
}
