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
         
           static ArrayList<Message> sentMessages = new ArrayList<>();
   static ArrayList<Message> storedMessages = new ArrayList<>();
   static Scanner scan = new Scanner(System.in);
   static com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().create();
   
   
    public static class Message{
       public static String storedMessageID;
       public String storedMessageHash;
       public static String storedRecipientCell;
       public static String storedMessage;
       public String storedStatus;
       static int totalMessagesSent = 0;

       public Message(String recipient, String message) {
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
       public static String createMessageHash() {
           String idPart = storedMessageID.substring(0, 2);
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

           System.out.print("Enter recipient number (+27XXXXXXXXX): ");
           String recipient = scan.nextLine();
       

           System.out.print("Enter Message: ");
           String messageText = scan.nextLine();

           // Requirement: Message max 250 chars
           if (messageText.length() > 250) {
               System.out.println("Please enter a message of less than 250 characters.");
               continue;
           }

           Message sms = new Message(recipient, messageText);
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

       switch (action) {
           case 1:
               sms.setStatus("Sent");
               Message.totalMessagesSent++;
               sentMessages.add(sms);
               storeMessageToJson(sms);
               System.out.println("Message successfully sent");
               sms.printDetails(); // Requirement 7
               break;

           case 2:
               sms.setStatus("Stored");
               sentMessages.add(sms);
               storeMessageToJson(sms);
               System.out.println("Message successfully stored.");
               break;

           case 0:
               sms.setStatus("Discarded");
               System.out.println("Press 0 to delete the message");
               break;

           default:
               System.out.println("Invalid choice. Message disregarded.");
               return "Invalid choice";
       }
       return "Process complete";
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
    
        
    

    



