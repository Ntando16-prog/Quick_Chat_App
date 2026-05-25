/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quick_chat_app;

import java.util.Scanner;

/**
 *
 * @author Muzi
 */
public class Login {
        //Instance variables to store user registration details
    //

    String storedUsername;
    String storedPassword;
    String storedCellPhoneNumber;
    String yourName;
    String yourSurname;
    
    //username and its condition
    //username must contain an underscore and be no more than 5 characters long.
    public boolean checkUserName (String username){
     if(username.contains("_") && username.length()==5){
         return true;
     }else{
         return false;
     }   
    }
    //creating a password return method 
     //password mustbe 8+ characters with a uppercase, number and a special character 
    public boolean checkPasswordComplexity (String password){
        
        // checking if all 4 conditions are met
        // checking them separately for readability
        boolean hasLength = password.length() >=8;
        boolean hasCapitalLetter = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=].*");
        
    {
      //return true if all conditions are met
        return hasLength && hasCapitalLetter && hasNumber && hasSpecialChar;  
    }    
    }
    //this is to check if the user has a south african number or not
    //cellphone number must be a South African number it must start with +27.
    public boolean checkCellPhoneNumber(String phoneNumber){
        if(phoneNumber.matches("^\\+27[0-9]{9}$")){
            return true;
        }else{
            return false;
        }
    }
    
    //creating a registration pane where the user will be asked to provide their details 
    public String registerUser (String username, String password, String phoneNumber){
        // conditions if username does not meet the requirements
        if(!checkUserName(username)){
            return "The username is incorrectly formatted";
        }
        // conditions if the password does not meet the requirements
        if (!checkPasswordComplexity (password)){
            return "password does not meet the complexity requirements";
        }
        // conditions if the cell phone number does not meet the requirement
        if (!checkCellPhoneNumber(phoneNumber)){
            return "Cell phone number incorrectly formatted or does not contain international code ";
        }
        storedUsername = username;
        storedPassword = password;
        storedCellPhoneNumber = phoneNumber;
        return "User successfully registered";
    }
    
    //Authenticates user login credentials against stored registration data
    // Used by returnLoginStatus to determain if login is successful.
    
    public boolean loginUser(String username, String password){
        // Check if input matches stored credentials from registration
        // Both username and password must  be correct for login to succed
        return username.equals(storedUsername) && password.equals(storedPassword);
    }
    
    
    public String returnLoginStatus(String username, String password){
        
        // Call loginUser method to verify credentials
        if (loginUser(username, password)){
            // Login successful - return personalized welcome message
            return "Welcome " + yourName + " " + yourSurname + ", it is great to see you again!";
           
        }else{
            // Login failed - return generic error meesage.
            return " Username or password incorrect please try again";
           
        }
    }
    
    public String handleLogin() {
         //creating a scanner
        Scanner userinput = new Scanner(System.in);
        //Create login object for testing
        Login store = new Login();
        
        
        //Letting the user to put in their details
        System.out.println("\n=====REGISTRATION=====");
        //Prompt the user to insert their name
        System.out.println("Enter First name:");
        store.yourName = userinput.nextLine();
        //Prompt the user to enter their surname
        System.out.println("Enter your surname:");
        store.yourSurname = userinput.nextLine();
        
        //Making sure that the user meets all conditions before going to the next step
        String username;
        do{
          System.out.println("Enter your username:");
        username = userinput.nextLine();
        if(!store.checkUserName(username)){
            System.out.println("Username is not correctly formatted. It must contain an '_' and be 5 characters long.");   
        }
        }while(!store.checkUserName(username));
        
        //Validating the password
        
        String password;
        do{
        System.out.println("Enter your password:");
         password = userinput.nextLine();
         if(!store.checkPasswordComplexity(password)){
             System.out.println("Password does not meet the complexity requirements. Needs to be 8+ chars, capital letter, number, and specia lchar.");   
         }
        }while(!store.checkPasswordComplexity(password));
        
        //Valid phone
        
        String phoneNumber;
        do{
         System.out.println("Enter your cell phone number:");
        phoneNumber =userinput.nextLine();
        if(!store.checkCellPhoneNumber(phoneNumber)){
            System.out.println("Number incorrectly formatted or does not contain international code.");
        }
        }while(!store.checkCellPhoneNumber(phoneNumber));
       
       
       //Call registerUser method with all captured details 
       //This method validates username, password, and stores user data
        
        String registerInformation = store.registerUser(username, password, phoneNumber);
        System.out.println(registerInformation);//Display registration result
        
        //======USER LOGIN SECTION=======
        //Only proceed to login if registeration was successful  
        
        if(registerInformation.equals("User successfully registered")){
        System.out.println("\n===== LOGIN =====");
        
        //Prompt for login credentials
        System.out.println("Enter your username: ");
        String loginUser =userinput.nextLine();
        System.out.println("Enter your password: ");
        String loginPassword = userinput.nextLine();
        
        
        
        //Call returnLoginStatus to verify credentials and get response.
        //This method internally calls loginUser to check if credentials match.
        String loginMessage = store.returnLoginStatus(loginUser, loginPassword);
        return loginMessage;
 
         
    }
        return "Registration failed, could not attempt login";
    }
}
    
    
    

