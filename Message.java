// Message.java
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Message {
    private String messageID;
    private String recipient;
    private String text;
    private String hash;

    public Message(String messageID, String recipient, String text) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.text = text;
        this.hash = Integer.toHexString((messageID + recipient + text).hashCode());
    }

    public boolean checkMessageID() {
        if (messageID == null || messageID.length() != 10) {
            System.out.println("Invalid message ID. Must be 10 digits.");
            return false;
        }
        return true;
    }

    public boolean checkRecipientCell() {
        if (!recipient.matches("^\\+27\\d{9}$")) {
            System.out.println("Invalid recipient cell. Must start with +27 and be 12 digits.");
            return false;
        }
        return true;
    }

    public void storeMessage() {
        JSONObject messageObj = new JSONObject();
        messageObj.put("id", messageID);
        messageObj.put("recipient", recipient);
        messageObj.put("message", text);

        try {
            JSONArray messages = new JSONArray();
            messages.add(messageObj);

            FileWriter writer = new FileWriter("stored_messages.json", true);
            writer.write(messages.toJSONString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to store message.");
        }
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

    public String getMessageHash() {
        return hash;
    }

    public String getMessageDetails() {
        return "Hash: " + hash + "\nID: " + messageID + "\nRecipient: " + recipient + "\nMessage: " + text + "\n";
    }
}
