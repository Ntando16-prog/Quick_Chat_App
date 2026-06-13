/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quick_chat_app;

import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Muzi
 */
public class ChatApp_Message {
    // Recipient validation: must ensure that its has : +27 + 9 digits
       public static String checkRecipientCell(String cell) {
          if (cell.matches("^\\+27\\d{9}$")){
             return "Cell phone number is successfully captured. "; 
          }
           return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again. ";   
       }
       
       //this method is used to check the length of the message and has condition that need to be followed
         public static String validateMessageLength(String msg){
           if(msg.length()<=250){
               return "Message ready to send.";
           }else{
               return"Message exceeds 250 characters by " + (msg.length()-250);
           }
       }
         
   static ArrayList<String> sentMessagesArray = new ArrayList<>();
   static ArrayList<String> storedMessagesArray = new ArrayList<>();
   static ArrayList<String> disregardedMessagesArray = new ArrayList<>();
   static ArrayList<String> storedMessageHashArray = new ArrayList<>();
   static ArrayList<String> storedMessageIDArray = new ArrayList<>();
   
   //used to track message senders and recipient
   static ArrayList<String> senderArray = new ArrayList<>();
   static ArrayList<String> recipientArray = new ArrayList<>();
   
   static Scanner scan = new Scanner(System.in);
   static com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().create();
   
   
    public static class Message{
       public String storedMessageID;
       public String storedMessageHash;
       public String storedRecipientCell;
       public String storedMessage;
       public String storedStatus;
       public String senderNum;
       static int totalMessagesSent = 0;

       public Message(String recipient, String message) {
           this.senderNum = "Unknown";//default when acting as sender
           this.storedRecipientCell = recipient;
           this.storedMessage = message;
           this.storedMessageID = generateMessageID();
           this.storedMessageHash = createMessageHash();
           this.storedStatus = "Draft";
       }
       
       public Message(String sender, String recipient, String message){
           this.senderNum = sender;
           this.storedRecipientCell = recipient;
           this.storedMessage = message;
           this.storedMessageID = generateMessageID();
           this.storedMessageHash = createMessageHash();
           this.storedStatus = "Draft";
       }
       public boolean checkMessageID(){
           return storedMessageID!= null && storedMessageID.length() <=10;
       }

       // 10-digit Message ID
       private String generateMessageID() {
           Random rand = new Random();
           long num = 1000000000L + rand.nextLong(9000000000L);
           return String.valueOf(num);
       }

    
       // Hash format: first2Digits:numberOfWords:FIRSTWORDLASTWORD
       public String createMessageHash() {
           String idPart = storedMessageID.length()>=2 ? storedMessageID.substring(0, 2):"00";
           String[] words = storedMessage.trim().split("\\s+");
           int wordCount = words.length;
           String firstWord = words[0].toUpperCase();
           String lastWord = words[words.length - 1].toUpperCase();
           return idPart + ":" + wordCount + ":" + firstWord + lastWord;
       }
     

       static int returnTotalMessage() {
           return totalMessagesSent;
       }

       public void setStatus(String status) {
           this.storedStatus = status;
       }

       // Requirement 7: Print in order Message ID, Message Hash, Recipient, Message
       void printDetails() {
           System.out.println("\n--- Message Details ---");
           System.out.println("Message ID: " + storedMessageID);
           System.out.println("Message Hash: " + storedMessageHash);
           System.out.println("Sender: " + senderNum);
           System.out.println("Recipient: " + storedRecipientCell);
           System.out.println("Message: " + storedMessage);
           System.out.println("Status: " + storedStatus);
       }
   }

   



            public static void main(String[] args){
               System.out.println("Welcome to QuickChat.");

       // Requirement 1: User must be logged in
        Login loginSystem = new Login();
        
        // user must meet all the registration/login requirements
        String loginMessage =loginSystem.handleLogin();
        if (loginMessage !=null && loginMessage.startsWith("Welcome")){
       int choice;
       do {
           // Requirement 3 & 4: Menu loops until quit
           System.out.println("\n--- QuickChat Menu ---");
           System.out.println("1. Send Messages");
           System.out.println("2. Coming Soon");
           System.out.println("3. Quit");
           System.out.println("4. Stored Messages");
           System.out.print("Enter choice: ");

           while (!scan.hasNextInt()) {
               System.out.print("Invalid input. Enter choice: ");
               scan.next();
           }
           choice = scan.nextInt();
           scan.nextLine();

           switch (choice) {
               case 1:
                   sentMessages();
                   break;
               case 2:
                   System.out.println("Coming Soon.");
                   break;
               case 3:
                   // Requirement 6: Display total messages on exit
                   System.out.println("Exiting. Total messages sent: " + Message.totalMessagesSent);
                   break;
               case 4:
                   storedMessagesSubMenu();
                   break;
               default:
                   System.out.println("Invalid choice. Try again.");
           }
       } while (choice!= 3);
   }else{
            System.out.println("Login failed. Exiting application.");
        }
            }

   public static boolean login() {
       System.out.print("Enter username: ");
       String user = scan.nextLine();
       System.out.print("Enter password: ");
       String pass = scan.nextLine();
       return true; // Change if needed
   }

   // Requirement 5: Ask how many messages upfront
   public static void sentMessages() {
       System.out.print("How many messages do you want to enter? ");
       while (!scan.hasNextInt()) {
           System.out.print("Invalid input. Enter a number: ");
           scan.next();
       }
       int num = scan.nextInt();
       scan.nextLine();

       for (int i = 0; i < num; i++) {
           System.out.println("\n--- Message " + (i + 1) + " of " + num + " ---");
           System.out.print("Are you entering a Sender number for the message/s? (yes/no): ");
           String hasSender = scan.nextLine().trim().toLowerCase();
           String sender = "Unknown";
           if(hasSender.equals("yes")){
               System.out.print("Enter sender number: ");
               sender= scan.nextLine();
           }
           
           System.out.print("Enter recipient number (+27XXXXXXXXX): ");
           String recipient = scan.nextLine();
       

           System.out.print("Enter Message: ");
           String messageText = scan.nextLine();

           // Requirement: Message max 250 chars
           if (messageText.length() > 250) {
               System.out.println("Please enter a message of less than 250 characters.");
               continue;
           }

           Message sms = new Message(sender, recipient, messageText);
           handleMessageAction(sms, 0);
       }
   }

   // Screenshot 3: Send/Disregard/Store options
   public static String handleMessageAction(Message sms, int action) {
       System.out.println("\nChoose an option: ");
       System.out.println("1. Send Message");
       System.out.println("2. Store Message to send later");
       System.out.println("0. Disregard Message");
       System.out.print("Enter choice: ");

       action = scan.nextInt();
       
       storedMessageIDArray.add(sms.storedMessageID);
       storedMessageHashArray.add(sms.storedMessageHash);
       senderArray.add(sms.senderNum);
       recipientArray.add(sms.storedRecipientCell);

       switch (action) {
           case 1:
               sms.setStatus("Sent");
               Message.totalMessagesSent++;
               sentMessagesArray.add(sms.storedMessage);
               disregardedMessagesArray.add("");
               storedMessagesArray.add("");
               
               storeMessageToJson(sms);
               System.out.println("Message successfully sent");
               sms.printDetails(); // Requirement 7
               break;

           case 2:
               sms.setStatus("Stored");
               sentMessagesArray.add("");
               disregardedMessagesArray.add("");
               storedMessagesArray.add(sms.storedMessage);
               
               storeMessageToJson(sms);
               System.out.println("Message successfully stored.");
               break;

           case 0:
               sms.setStatus("Discarded");
               sentMessagesArray.add("");
               disregardedMessagesArray.add(sms.storedMessage);
               storedMessagesArray.add("");
               
               System.out.println("Message successfully diregarded. ");
               break;

           default:
               
               System.out.println("Invalid choice. Message disregarded.");
               sms.setStatus("Disregarded");
               sentMessagesArray.add("");
               disregardedMessagesArray.add(sms.storedMessage);
               storedMessagesArray.add("");
               break;
       }
       return "Process complete";
   }
       // --- NEW MENU STORED MESSAGES ---
    public static void storedMessagesSubMenu() {
        int option;
        do {
            System.out.println("\n--- Stored Messages Sub-Menu ---");
            System.out.println("1. Display sender and recipient of all stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message ID");
            System.out.println("4. Search for all messages stored for a particular recipient");
            System.out.println("5. Delete a message using the message hash");
            System.out.println("6. Display full report");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter selection: ");
            
            option = scan.nextInt();
            scan.nextLine(); 

            switch (option) {
                case 1: 
                    displaySendersAndRecipients(); 
                    break;
                case 2: 
                    displayLongestStoredMessage(); 
                    break;
                case 3: 
                    searchByMessageId(); 
                    break;
                case 4: 
                    searchByRecipient(); 
                    break;
                case 5: 
                    deleteByHash(); 
                    break;
                case 6: 
                    displayFullReport(); 
                    break;
                case 0:
                    System.out.println("Return to main menu");
                default: 
                    System.out.println("Invalid choice.");
            }
        } while (option != 0);
    }

    // Displaying sender and recipient of all stored messages
    public static void displaySendersAndRecipients() {
        System.out.println("\n--- Senders and Recipients of Stored Messages ---");
        boolean found = false;
        for (int i = 0; i < storedMessagesArray.size(); i++) {
            if (!storedMessagesArray.get(i).isEmpty()) {
                System.out.println("Stored Message Slot [" + i + "]: Sender: " + senderArray.get(i) + " | Recipient: " + recipientArray.get(i));
                found = true;
            }
        }
        if (!found) System.out.println("No 'Stored' messages found.");
    }

    // Displaying the longest stored message
    public static void displayLongestStoredMessage() {
        String longest = "";
        for (String msg : storedMessagesArray) {
            if(msg != null && !msg.trim().isEmpty()){
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        }
        // Fallback search checking sent array items if assignment merges operations
        if (!longest.isEmpty()) {
            for (String msg : sentMessagesArray) {
                if (msg.length() > longest.length()) {
                    longest = msg;
                }
            }
        }
        if (!longest.isEmpty()) {
            System.out.println("\nLongest Message found: \"" + longest + "\"");
        } else {
            System.out.println("\nNo messages found.");
        }
    }

    // Search for a message ID and display corresponding recipient and message
    public static void searchByMessageId() {
        System.out.print("Enter Message ID to search: ");
        String searchId = scan.nextLine().trim();
        int index = storedMessageIDArray.indexOf(searchId);
        
        if (index != -1) {
            String content = !storedMessagesArray.get(index).isEmpty() ? storedMessagesArray.get(index) : sentMessagesArray.get(index);
            if(content.isEmpty()) content = disregardedMessagesArray.get(index);
            
            System.out.println("\nSystem Returns:");
            System.out.println("Recipient: " + recipientArray.get(index));
            System.out.println("Message: \"" + content + "\"");
        } else {
            System.out.println("Message ID not found.");
        }
    }

    // Search for all messages sent or stored regarding a particular recipient
    public static void searchByRecipient() {
        System.out.print("Enter Recipient Number (e.g. +27838884567): ");
        String target = scan.nextLine().trim();
        System.out.println("\nSystem Returns:");
        boolean found = false;
        
        for (int i = 0; i < recipientArray.size(); i++) {
            if (recipientArray.get(i).equals(target)) {
                String msgContent = "";
                if (!sentMessagesArray.get(i).isEmpty()) msgContent = sentMessagesArray.get(i);
                else if (!storedMessagesArray.get(i).isEmpty()) msgContent = storedMessagesArray.get(i);
                
                if(!msgContent.isEmpty()){
                    System.out.println("- \"" + msgContent + "\"");
                    found = true;
                }
            }
        }
        if (!found) System.out.println("No matching sent or stored messages found for this recipient.");
    }

    // Delete a message using the message hash
    public static void deleteByHash() {
        System.out.print("Enter Message Hash to delete: ");
        String targetHash = scan.nextLine().trim();
        int index = storedMessageHashArray.indexOf(targetHash);
        
        if (index != -1) {
            String targetMsg = "";
            if (!storedMessagesArray.get(index).isEmpty()) targetMsg = storedMessagesArray.get(index);
            else if (!sentMessagesArray.get(index).isEmpty()) targetMsg = sentMessagesArray.get(index);
            else targetMsg = disregardedMessagesArray.get(index);
            
            // Wipe data from parallel tracking collections
            storedMessageIDArray.remove(index);
            storedMessageHashArray.remove(index);
            senderArray.remove(index);
            recipientArray.remove(index);
            sentMessagesArray.remove(index);
            disregardedMessagesArray.remove(index);
            storedMessagesArray.remove(index);
            
            System.out.println("\nThe system returns:");
            System.out.println("Message: \"" + targetMsg + "\" successfully deleted.");
        } else {
            System.out.println("Hash mismatch or not found.");
        }
    }

    // Display full report
    public static void displayFullReport() {
        System.out.println("\n=== SYSTEM COMPLETE STORAGE REPORT ===");
        if(storedMessageIDArray.isEmpty()) {
            System.out.println("No data entries captured yet.");
            return;
        }
        for (int i = 0; i < storedMessageIDArray.size(); i++) {
            System.out.println("\nRecord [" + (i + 1) + "]");
            System.out.println("ID: " + storedMessageIDArray.get(i));
            System.out.println("Hash: " + storedMessageHashArray.get(i));
            System.out.println("Sender: " + senderArray.get(i));
            System.out.println("Recipient: " + recipientArray.get(i));
            
            String status = "Disregarded";
            String msg = disregardedMessagesArray.get(i);
            if (!sentMessagesArray.get(i).isEmpty()) {
                status = "Sent";
                msg = sentMessagesArray.get(i);
            } else if (!storedMessagesArray.get(i).isEmpty()) {
                status = "Stored";
                msg = storedMessagesArray.get(i);
            }
            System.out.println("Status/Flag: " + status);
            System.out.println("Message Contents: " + msg);
            System.out.println("------------------------------------");
        }
    }

   public static void storeMessageToJson(Message sms) {
       try (FileWriter writer = new FileWriter("messages.json", true)) {
           gson.toJson(sms, writer);
           writer.write("\n");
       } catch (IOException e) {
           System.out.println("Error saving to JSON: " + e.getMessage());
       }
   }
}
    
        
    

    



