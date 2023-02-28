/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Avadh
 */
public class Login {
    
    private boolean matched;
    
    public boolean checkUser(JFrame frame, String username, String password, String usertype) 
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        AccessControlSystem program = new AccessControlSystem();
                
        try {
        Connection conn = program.DBConnect();
        
        String query = "SELECT Password, Username, UserType FROM login WHERE "
                + "Username = '" + username + "' "
                + "AND UserType = '" + usertype + "'";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        
        
        
        while (result.next()) {
            LoginCurrent.username = result.getString("Username");
            LoginCurrent.userType = result.getString("UserType");
            String storedPass = result.getString("Password");
            
            matched = checkPassword(password, storedPass);
            
            if (matched) {
                return true;
            }
            else {
                return false;
            }
            
        }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
        return false;

    }
    
    public String hashPBKDF(String text) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iter = 1000;
        char[] chars = text.toCharArray();
        byte[] salt = getSalt();
        
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iter, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iter + ":" + toHex(salt) + ":" + toHex(hash);
        
    }
    
    public byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    
    public String toHex(byte[] array) {
        BigInteger i = new BigInteger(1, array);
        String hex = i.toString(16);
        
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }
        else {
            return hex;
        }
    }
    
    public boolean checkPassword(String inputPass, String storedPass) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String[] section = storedPass.split(":");
        int iter = Integer.parseInt(section[0]);
        
        byte[] salt = fromHex(section[1]);
        byte[] hash = fromHex(section[2]);
        
        PBEKeySpec spec = new PBEKeySpec(inputPass.toCharArray(), 
            salt, iter, hash.length * 8);
        
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        
        byte[] hashTest = skf.generateSecret(spec).getEncoded();
        
        // XOR operation on the length of hash and hashTest
        int diff = hash.length ^ (hashTest.length);
        
        for (int i = 0; i < hash.length && i < hashTest.length; i ++) {
            // Bitwise OR operator applied to diff and the XOR of the an element
            // in hash and an element in hashTest
            diff |= hash[i] ^ hashTest[i];  
        }

        return diff == 0;
    }
    
    public byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        
        return bytes;
    }
    
    public void updateLog(String username, String type) {
        AccessControlSystem program = new AccessControlSystem();
        try {
            Connection conn = program.DBConnect();
            
            //Get current date and time, ie the date and time of login
            java.util.Date date = new java.util.Date();
            java.sql.Date SQLDate = new  java.sql.Date(date.getTime());
            Timestamp SQLTime = new Timestamp(date.getTime());
            
            String entry = "[" + type + "]" + " user " + username + " has logged in.";
            
            String query = "INSERT INTO log(DateOfEntry, TimeOfEntry, Entry) "
                    + "VALUES(?, ?, '"+ entry +"')";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, SQLDate);
            stmt.setTimestamp(2, SQLTime);
            stmt.executeUpdate();
            stmt.close();
            
        }
        catch (SQLException e){
            System.err.println(e);
        }
    }
    
    public static void main(String[] args) {
        
    }
}
