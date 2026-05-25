package com.mycompany.quick_chat_app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import com.mycompany.quick_chat_app.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
   Login store = new Login();
   
   //Test: Username correctly formatted
   //returns true for username with underscore and <= 5 characters.
   
   @Test
   public void testUsernameCorrectlyFormatted() {
       assertTrue(store.checkUserName("kyl_1"));
   }
   
   //Test: Username incorrectly formatted 
   //returns false for username without an underscore or > 5 characters.
   @Test
   public void testUsernameIncorrectlyFormatted() {
       assertFalse(store.checkUserName("kyle!!!!!!!"));
   }
   
   //Test: Password meets complexity
   // returns true for password that contains all that is required from the conditions
   
   @Test
   public void testPasswordMeetsComplexity() {
       assertTrue(store.checkPasswordComplexity("Ch&&sec@ke99!"));
   }
   
   //Test: password is incorrectly formatted
   //returns false for password that does not meet the required conditions
   @Test
   public void testPasswordDoesNotMeetComplexity() {
       
       //act
       boolean result = store.checkPasswordComplexity("password");
       assertFalse(result);
   }
   
   //Test: cellphone number is correctly formatted and contains a +27 representing a South African code
   //returns true for a cellphone number that starts with the South African code +27
   @Test
   public void testCellPhoneCorrectlyFormatted() {
       assertTrue(store.checkCellPhoneNumber("+27838968976"));
   }
   
   //Test: cellphone number does start with a South African code meaning it is incorrecly formatted
   //returns false for cellphone number that does not have the South African code
   @Test
   public void testCellPhoneIncorrectlyFormatted() {
       assertFalse(store.checkCellPhoneNumber("08966553"));
   }
   
   
   @Test
   public void testLoginSuccessful(){
       //Arranging the valid credentials from requirements
       store.registerUser("kyl_1", "Ch&&sec@ke99!", "+27831234567");
       String username = "kyl_1";
       String password = "Ch&&sec@ke99!";
       
       //attempt login
       boolean result = store.loginUser(username,password);
       //Assert: login should proceed
       assertTrue(result);
   }
   
   //Test: Failed login with invalid credentials
   //return false when username or password incorrect
   @Test
   public void testLoginFailed(){
       boolean result = store.loginUser("kyle!!!!!!!", "password");
       //Assert: login details incorrect user needs to check if their details match the register
       assertFalse(result);
   }

}
