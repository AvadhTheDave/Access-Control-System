/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Avadh
 */
public class ChangeRole {
    
    public static String user;
    
    public void updateRole(String user, String role) {
        AccessControlSystem program = new AccessControlSystem();
        
        try {
            Connection conn = program.DBConnect();
            
            String query = "UPDATE login SET Role = '" + role + "' "
                    + "WHERE Username = '" + user + "'";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.execute();
            stmt.close();
            System.out.println("role updated");
        }
        catch (SQLException e){
            System.err.println(e);
        }
    }
}
