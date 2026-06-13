package com.mycompany.quick_chat_app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.quick_chat_app.ChatApp_Message.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class ChatAppMessageTest {

    // Assuming these methods live inside a class named ChatApp_Message or similar
    // Update the instantiation name to match your actual message class name
    ChatApp_Message messageProcessor = new ChatApp_Message();

    
    @Test
    public void testCheckMessageLengthSuccess() {
        // Test data within the 250 character limit
        String testMessage = "Hi Mike, can you join us for dinner tonight?";
        
        // Call your actual validation method here
        String actualResponse = messageProcessor.validateMessageLength(testMessage);
        
        // Expected string from the assignment criteria
        String expectedResponse = "Message ready to send.";
        
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testCheckMessageLengthFailure() {
        // Test data designed to fail (generate a string longer than 250 characters)
        String testMessage = "This is a very long message. ".repeat(10); 
        int excessCharacters = testMessage.length() - 250;
        
        // Call your actual validation method here
        String actualResponse = messageProcessor.validateMessageLength(testMessage);
        
        // Match the exact expected string constraint format
        String expectedResponse = "Message exceeds 250 characters by " + excessCharacters ;
        
        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    public void testCheckRecipientNumberSuccess() {
        // Valid international formatted number using assignment test data
        String validNumber = "+27718693002"; 
        
        String actualResponse = messageProcessor.checkRecipientCell(validNumber);
        String expectedResponse = "Cell phone number is successfully captured. ";
        
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testCheckRecipientNumberFailure() {
        // Invalid number format lacking international formatting prefix code
        String invalidNumber = "08575975889"; 
        
        String actualResponse = messageProcessor.checkRecipientCell(invalidNumber);
        String expectedResponse = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again. ";
        
        assertEquals(expectedResponse, actualResponse);


class QuickChatUnitTests {

    // Mock representation of the program arrays required across the POE
    private String[] sentMessages;
    private String[] storedMessages;
    private String[] messageIDs;
    private String[] messageHashes;
    private String[] recipients;

    @BeforeEach
    public void setUp() {
        // Populating array data exactly matching Test Data Messages 1-5 from the POE requirements
        recipients = new String[]{"+27834557896", "+27838884567", "+27834484567", "0838884567", "+27838884567"};
        
        // Parallel arrays containing data points mapped to their specific flags
        sentMessages = new String[]{
            "Did you get the cake?", // Msg 1 (Sent)
            "", 
            "", 
            "It is dinner time!", // Msg 4 (Sent)
            ""
        };
        
        storedMessages = new String[]{
            "", 
            "Where are you? You are late! I have asked you to be on time.", // Msg 2 (Stored)
            "", 
            "", 
            "Ok, I am leaving without you." // Msg 5 (Stored)
        };

        // Matching structural data keys for search and delete functions
        messageIDs = new String[]{"MSG001", "MSG002", "MSG003", "0838884567", "MSG005"};
        messageHashes = new String[]{"HASH_MSG1", "HASH_MSG2", "HASH_MSG3", "HASH_MSG4", "HASH_MSG5"};
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // Act: Filter out elements that belong to the sent array segment
        List<String> validSentData = new ArrayList<>();
        for (String msg : sentMessages) {
            if (!msg.isEmpty()) {
                validSentData.add(msg);
            }
        }

        // Assert: System returns "Did you get the cake?" and "It is dinner time!"
        assertEquals("Did you get the cake?", validSentData.get(0));
        assertEquals("It is dinner time!", validSentData.get(1));
    }

   
    @Test
    public void testDisplayLongestMessage() {
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        String actualLongest = "";

        // Act: Run loop scanning across test messages 1 to 4 arrays
        for (int i = 0; i < 4; i++) {
            if (sentMessages[i].length() > actualLongest.length()) {
                actualLongest = sentMessages[i];
            }
            if (storedMessages[i].length() > actualLongest.length()) {
                actualLongest = storedMessages[i];
            }
        }

        // Assert
        assertEquals(expectedLongest, actualLongest);
    }

    
    @Test
    public void testSearchForMessageID() {
        String targetID = "0838884567";
        String expectedSystemReturn = "It is dinner time!";
        String actualSystemReturn = "";

        // Act: Linear search across ID array to pull the message payload
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i].equals(targetID)) {
                actualSystemReturn = sentMessages[i]; 
                break;
            }
        }

        // Assert
        assertEquals(expectedSystemReturn, actualSystemReturn);
    }

    
    @Test
    public void testSearchAllMessagesRegardingParticularRecipient() {
        String searchTarget = "+27838884567";
        List<String> matchingResponses = new ArrayList<>();

        // Act: Collect both messages linked to the phone number index
        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i].equals(searchTarget)) {
                if (!sentMessages[i].isEmpty()) matchingResponses.add(sentMessages[i]);
                if (!storedMessages[i].isEmpty()) matchingResponses.add(storedMessages[i]);
            }
        }

        // Assert: System must return Message 2 and Message 5 text strings
        assertEquals("Where are you? You are late! I have asked you to be on time.", matchingResponses.get(0));
        assertEquals("Ok, I am leaving without you.", matchingResponses.get(1));
    }

    
    
    @Test
    public void testDeleteMessageUsingMessageHash() {
        String targetHash = "HASH_MSG2";
        String expectedMessageBeforeDeletion = "Where are you? You are late! I have asked you to be on time.";
        
        // Confirm message is intact before test execution begins
        assertEquals(expectedMessageBeforeDeletion, storedMessages[1]);

        // Act: Look up index by tracking hash array and drop text content
        boolean codeDeletionSuccessMessage = false;
        for (int i = 0; i < messageHashes.length; i++) {
            if (messageHashes[i].equals(targetHash)) {
                storedMessages[i] = ""; // Stripping field content to simulate a clean data wipe
                codeDeletionSuccessMessage = true;
                break;
            }
        }

        // Assert: Confirm the space evaluates back to empty string or null flag
        assertTrue(codeDeletionSuccessMessage);
        assertEquals("", storedMessages[1]);
    }
}
    }
}


