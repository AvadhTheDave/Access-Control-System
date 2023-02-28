/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Avadh
 */
public class FileManager {
    
    public byte[] initVector = null;
    public String keyString = "";
    

    public void addFile(JFrame frame, File file, String fileName, String dest) 
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, 
            BadPaddingException, InvalidKeyException, 
            InvalidAlgorithmParameterException, IllegalBlockSizeException {
        String text = "";
        
        String src = file.getAbsolutePath();
        System.out.println(src);
        System.out.println(fileName);
                
        Path source = Paths.get(src);
        Path destination = Paths.get(dest + "\\files\\" + fileName);
        
        try {
            Files.copy(source, destination);
            text = convertFileToString(destination);
            
            // Overwrites text file with encrypted ciphertext
            try {
                String path = destination.toString();
                FileWriter writer = new FileWriter(path, false);
                writer.write(text);
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            updateDB(frame, fileName, initVector, keyString);
            
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(frame, "Error: " + e);
        }
        
        
    }
    
    public void openFile(JFrame frame, String dir, String key, String file) throws IOException, NoSuchAlgorithmException {
        AccessControlSystem program = new AccessControlSystem();
        String actualKey = "";
        String plaintext = "";
        try {
            Connection conn = program.DBConnect();
            String query = "SELECT SecretKey FROM resource WHERE "
                    + "FileName = '" + file + "'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                actualKey = result.getString("SecretKey");
            }
            
            if ((actualKey.compareTo(key)) == 0) {
                plaintext = decryptFile(file, key);
            }
            else {
                JOptionPane.showMessageDialog(frame, "Wrong Key: Please enter"
                        + "the correct key to decrypt "+ file +".");
            }
            
        }
        catch (SQLException e){
            System.out.println("Error: " + e);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeFile(String fileName) {
        String dir = System.getProperty("user.dir");
        Path destination = Paths.get(dir + "\\files\\" + fileName);
        File file = destination.toFile();
        
        file.delete();
    }
    
    public void updateDB(JFrame frame, String name, byte[] vector, String key) {
        AccessControlSystem program = new AccessControlSystem();
        
        try {
            Connection conn = program.DBConnect();
            String query = "INSERT INTO resource (FileName, Role) VALUES ('" + name + "', 'test')";
            
            System.out.println(query);
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.execute();
            
            conn.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }
    
    public void updateFileRole(JFrame frame, String file, String role) {
        AccessControlSystem program = new AccessControlSystem();
        
        try {
            Connection conn = program.DBConnect();
            String query = "UPDATE resource SET Role = "
                    + "'" + role + "' WHERE "
                    + "FileName = '" + file + "'";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.execute();
            conn.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }
    
    public String encryptFile(Path dest) throws NoSuchAlgorithmException, 
            NoSuchPaddingException, InvalidKeyException, 
            InvalidAlgorithmParameterException, IllegalBlockSizeException, 
            BadPaddingException {
        
        String plaintext = convertFileToString(dest);
        Encryption encrypt = new Encryption();
        
        SecretKey key = encrypt.createKey();
        initVector = encrypt.createVector();
        
        
        byte[] decrypted = encrypt.executeEncryption(plaintext, key, initVector);
        
        String ciphertext = DatatypeConverter.printHexBinary(decrypted);
        
        byte[] raw = key.getEncoded();
        keyString = Base64.getEncoder().encodeToString(raw);
        
        
        System.out.println(ciphertext);
        System.out.println(initVector);
        System.out.println(keyString);
        
        
        return ciphertext;
    }
    
    public String decryptFile(String fileName, String key) throws IOException, 
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
            InvalidAlgorithmParameterException, IllegalBlockSizeException, 
            BadPaddingException, BadPaddingException {
        String dir = System.getProperty("user.dir");
        
        Path destination = Paths.get(dir + "\\files\\" + fileName);
        
        String ciphertext = convertFileToString(destination);
        byte[] vector = null;
        AccessControlSystem program = new AccessControlSystem();
        try {
            Connection conn = program.DBConnect();
            String query = "SELECT Vector FROM resource WHERE "
                    + "FileName = '" + fileName + "'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                vector = result.getBytes("Vector");
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        byte[] decodedKey = Base64.getDecoder().decode(key);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        byte[] cipher = ciphertext.getBytes();
        
        Encryption encrypt = new Encryption();
        String text = encrypt.executeDecryption(cipher, secretKey, vector);
        
        
        return text;
    }
    
    
    
    public String convertFileToString(Path dest) {
        StringBuilder build = new StringBuilder();
        String text = "";
        
        try {
            byte[] bytes = Files.readAllBytes(dest);
            text = new String(bytes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return text;
    }
    
}
