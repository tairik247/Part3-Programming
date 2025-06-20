// MessageTest.java
public class MessageTest {
    public static void main(String[] args) {
        testCheckMessageID();
        testCheckRecipientCell();
    }

    static void testCheckMessageID() {
        Message msg = new Message("1234567890", "+27831234567", "Test message");
        assert msg.checkMessageID() : "Message ID check failed.";
        System.out.println("✅ testCheckMessageID passed");
    }

    static void testCheckRecipientCell() {
        Message msg1 = new Message("1234567890", "+27831234567", "Hello");
        Message msg2 = new Message("1234567890", "0831234567", "Hello");

        assert msg1.checkRecipientCell() : "Valid recipient check failed.";
        assert !msg2.checkRecipientCell() : "Invalid recipient check failed.";
        System.out.println("✅ testCheckRecipientCell passed");
    }
}
