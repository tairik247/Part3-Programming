// QuickChatApp.java
import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QuickChatApp {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static ArrayList<Message> disregardedMessages = new ArrayList<>();
    static ArrayList<Message> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();
    static int totalMessagesSent = 0;
    static int messageCounter = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to QuickChat.");
        if (!login()) return;

        System.out.print("Enter how many messages you wish to send: ");
        int messageLimit = scanner.nextInt();
        scanner.nextLine();

        loadStoredMessages();

        while (true) {
            System.out.println("\nChoose an option:\n1) Send Message\n2) Show recently sent messages\n3) Display Report\n4) Search\n5) Quit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (sentMessages.size() >= messageLimit) {
                        System.out.println("Message limit reached.");
                    } else {
                        sendMessage();
                    }
                    break;
                case "2":
                    for (Message m : sentMessages) {
                        System.out.println(m.getMessageDetails());
                    }
                    break;
                case "3":
                    displayReport();
                    break;
                case "4":
                    performSearches();
                    break;
                case "5":
                    System.out.println("Exiting... Total messages sent: " + totalMessagesSent);
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static boolean login() {
        System.out.print("Enter username: ");
        String user = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();
        return user.equals("admin") && pass.equals("admin123");
    }

    static void sendMessage() {
        messageCounter++;
        String messageID = String.format("%010d", messageCounter);

        System.out.print("Enter recipient cell number (with international code): ");
        String recipient = scanner.nextLine();

        System.out.print("Enter your message: ");
        String msgText = scanner.nextLine();

        Message msg = new Message(messageID, recipient, msgText);

        if (!msg.checkMessageID() || !msg.checkRecipientCell()) return;
        if (msgText.length() > 250) {
            System.out.println("Message exceeds 250 characters by " + (msgText.length() - 250) + ", please reduce size.");
            return;
        }

        System.out.println("1) Send\n2) Disregard\n3) Store");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                sentMessages.add(msg);
                messageHashes.add(msg.getMessageHash());
                messageIDs.add(msg.getMessageID());
                totalMessagesSent++;
                JOptionPane.showMessageDialog(null, msg.getMessageDetails());
                System.out.println("Message successfully sent.");
                break;
            case "2":
                disregardedMessages.add(msg);
                System.out.println("Message disregarded.");
                break;
            case "3":
                storedMessages.add(msg);
                msg.storeMessage();
                System.out.println("Message successfully stored.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    static void loadStoredMessages() {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("stored_messages.json");
            JSONArray messagesArray = (JSONArray) parser.parse(reader);
            for (Object obj : messagesArray) {
                JSONObject msgJSON = (JSONObject) obj;
                String id = (String) msgJSON.get("id");
                String rec = (String) msgJSON.get("recipient");
                String msg = (String) msgJSON.get("message");
                storedMessages.add(new Message(id, rec, msg));
            }
        } catch (IOException | ParseException e) {
            System.out.println("No stored messages to load.");
        }
    }

    static void performSearches() {
        System.out.println("1) Search by Message ID\n2) Search by Recipient\n3) Delete by Message Hash\n4) Longest Message");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                System.out.print("Enter Message ID: ");
                String id = scanner.nextLine();
                for (Message m : sentMessages) {
                    if (m.getMessageID().equals(id)) {
                        System.out.println("Recipient: " + m.getRecipient() + ", Message: " + m.getText());
                    }
                }
                break;
            case "2":
                System.out.print("Enter Recipient: ");
                String rec = scanner.nextLine();
                for (Message m : sentMessages) {
                    if (m.getRecipient().equals(rec)) {
                        System.out.println("Message: " + m.getText());
                    }
                }
                for (Message m : storedMessages) {
                    if (m.getRecipient().equals(rec)) {
                        System.out.println("(Stored) Message: " + m.getText());
                    }
                }
                break;
            case "3":
                System.out.print("Enter Message Hash to delete: ");
                String hash = scanner.nextLine();
                Iterator<Message> iterator = sentMessages.iterator();
                while (iterator.hasNext()) {
                    Message m = iterator.next();
                    if (m.getMessageHash().equals(hash)) {
                        iterator.remove();
                        System.out.println("Message \"" + m.getText() + "\" successfully deleted.");
                        return;
                    }
                }
                System.out.println("Message not found.");
                break;
            case "4":
                Message longest = null;
                for (Message m : sentMessages) {
                    if (longest == null || m.getText().length() > longest.getText().length()) {
                        longest = m;
                    }
                }
                if (longest != null) {
                    System.out.println("Longest Message: " + longest.getText());
                }
                break;
            default:
                System.out.println("Invalid selection.");
        }
    }

    static void displayReport() {
        System.out.println("\n--- Sent Messages Report ---");
        for (Message m : sentMessages) {
            System.out.println(m.getMessageDetails());
        }
    }
}
