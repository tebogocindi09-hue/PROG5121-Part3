/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

/**
 *
 * @author Student
 */

package com.mycompany.messageapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // USERNAME TESTS

    @Test
    public void testCheckUserName_Valid() {
        Login user = new Login(
                "Thabo", "Mokoena",
                "th_1", "Password1!",
                "+27821234567"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testCheckUserName_Invalid() {
        Login user = new Login(
                "Thabo", "Mokoena",
                "thabo123", "Password1!",
                "+27821234567"
        );

        assertFalse(user.checkUserName());
    }

    // PASSWORD TESTS

    @Test
    public void testCheckPasswordComplexity_Valid() {
        Login user = new Login(
                "Thabo", "Mokoena",
                "th_1", "Password1!",
                "+27821234567"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        Login user = new Login(
                "Thabo", "Mokoena",
                "th_1", "password",
                "+27821234567"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    // CELL PHONE TESTS

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        Login user = new Login(
                "Thabo", "Mokoena",
                "th_1", "Password1!",
                "+27821234567"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        Login user = new Login(
                "Thabo", "Mokoena",
                "th_1", "Password1!",
                "0821234567"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    // PART 3 MESSAGE MANAGER TESTS

    @Test
    public void testStoreMessage() {
        MessageManager manager = new MessageManager(10);
        Message message = new Message(
                1, "Thabo Mokoena",
                "+27821234567", "Hello QuickChat"
        );

        assertEquals(
                "Message successfully stored.",
                manager.addStoredMessage(message)
        );
        assertEquals(1, manager.getStoredCount());
    }

    @Test
    public void testDisplaySendersAndRecipients() {
        MessageManager manager = new MessageManager(10);
        Message message = new Message(
                1, "Thabo Mokoena",
                "+27821234567", "Hello QuickChat"
        );

        manager.addStoredMessage(message);

        String result = manager.displaySendersAndRecipients();

        assertTrue(result.contains("Thabo Mokoena"));
        assertTrue(result.contains("+27821234567"));
    }

    @Test
    public void testSearchByMessageID() {
        MessageManager manager = new MessageManager(10);
        Message message = new Message(
                1, "Thabo Mokoena",
                "+27821234567", "Hello QuickChat"
        );

        manager.addStoredMessage(message);

        String result = manager.searchByMessageID(
                message.getMessageID()
        );

        assertTrue(result.contains("Hello QuickChat"));
    }

    @Test
    public void testSearchByRecipient() {
        MessageManager manager = new MessageManager(10);
        Message message = new Message(
                1, "Thabo Mokoena",
                "+27821234567", "Hello QuickChat"
        );

        manager.addStoredMessage(message);

        String result = manager.searchByRecipient("+27821234567");

        assertTrue(result.contains("Hello QuickChat"));
    }

    @Test
    public void testDeleteMessageByHash() {
        MessageManager manager = new MessageManager(10);
        Message message = new Message(
                1, "Thabo Mokoena",
                "+27821234567", "Hello QuickChat"
        );

        manager.addStoredMessage(message);

        String result = manager.deleteByMessageHash(
                message.getMessageHash()
        );

        assertEquals("Message successfully deleted.", result);
        assertEquals(0, manager.getStoredCount());
    }

    @Test
    public void testDisplayLongestMessage() {
        MessageManager manager = new MessageManager(10);

        Message shortMessage = new Message(
                1, "Thabo Mokoena",
                "+27821234567", "Hello"
        );

        Message longMessage = new Message(
                2, "Thabo Mokoena",
                "+27821234567",
                "This is the longest message in this test"
        );

        manager.addStoredMessage(shortMessage);
        manager.addStoredMessage(longMessage);

        String result = manager.displayLongestMessage();

        assertTrue(result.contains(
                "This is the longest message in this test"
        ));
    }
}
