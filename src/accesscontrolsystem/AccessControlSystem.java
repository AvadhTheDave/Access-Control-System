/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;

/**
 *
 * @author Avadh
 */
public class AccessControlSystem {
    
    private static final String username = "root";
    private static final String password = "Stolendance00"; 
    private static final String dbURL = "jdbc:mysql://localhost:3306/accesscontroldb";
    
    public Connection DBConnect() throws SQLException {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to Database!");
        }
        catch (SQLException e){
            System.err.println(e);
        }
        return conn;
    }
    
    public ResultSet getLog(String query) {
        
        try {
            Connection conn = DBConnect();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            ResultSet result = stmt.executeQuery();
            
            return result;
        }
        catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
    /**
     * @param args the command line arguments   
     */
    public static void main(String[] args) throws SQLException {
        AccessControlSystem program = new AccessControlSystem();
        Connection conn = program.DBConnect();
        
        new LoginForm().setVisible(true);
        
    }
}
