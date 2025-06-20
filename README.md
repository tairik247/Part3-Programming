# Part3-Programming

Step 1: Organise Files

1. Create a folder named `QuickChat` on your Desktop.
 2. Place the following files inside the folder:
   - QuickChatApp.java
   - Message.java
   - MessageTest.java (optional for testing)
   - json-simple-1.1.1.jar (download from https://code.google.com/archive/p/json-simple/downloads)

Step 2: Open Command Prompt

1. Press Windows + R, type `cmd`, and hit Enter.
 2. Navigate to the QuickChat folder using:
   cd C:\Users\<YourUsername>\Desktop\QuickChat

Step 3: Compile the Java Files
Run the following command to compile all Java files with the external JSON library:
 javac -cp .;json-simple-1.1.1.jar QuickChatApp.java Message.java

Step 4: Run the Application
Use the following command to start the program:
 java -cp .;json-simple-1.1.1.jar QuickChatApp

Step 5: Run Unit Tests (Optional)
To run unit tests (if MessageTest.java exists), use:
 java -cp .;json-simple-1.1.1.jar MessageTest

Notes:
Ensure json-simple-1.1.1.jar is in the same folder.
You must be authenticated with username: admin and password: admin123 to proceed.
Stored messages are read from a file named `stored_messages.json`.
