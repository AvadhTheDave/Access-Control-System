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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Avadh
 */
public class Register {
    
    public void addUser(JFrame frame, String username, String password, String usertype, String role) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        AccessControlSystem program = new AccessControlSystem();
        String hashedPassword = hashPBKDF(password);
        
        try {
            Connection conn = program.DBConnect();
            
            String query = "INSERT INTO login (Username, Password, UserType, Role) "
                    + "VALUES ('"
                    + username +
                    "', '"
                    + hashedPassword +
                    "', '"
                    + usertype +
                    "', '"
                    + role +
                    "')";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.execute();
            
            conn.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
        
        System.out.println(hashedPassword);
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
    
}
