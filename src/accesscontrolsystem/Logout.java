/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.JFrame;

/**
 *
 * @author Avadh
 */
public class Logout {
    public static void logOut(JFrame context, LoginForm login) {
        LoginCurrent.isLogged = false;
        context.setVisible(false);
        login.setVisible(true);
    }
    
    public static void updateLog(String username, String type) {
        AccessControlSystem program = new AccessControlSystem();
        try {
            Connection conn = program.DBConnect();
            
            //Get current date and time, ie the date and time of login
            java.util.Date date = new java.util.Date();
            java.sql.Date SQLDate = new  java.sql.Date(date.getTime());
            Timestamp SQLTime = new Timestamp(date.getTime());
            
            String entry = "[" + type + "]" + " user " + username + " has logged out.";
            
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
}
