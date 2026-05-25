package com.mycompany.quick_chat_app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.quick_chat_app.ChatApp_Message.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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
    }
}

