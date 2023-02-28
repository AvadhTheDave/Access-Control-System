/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import javax.swing.JFrame;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Avadh
 */
public class LoginTest {
    
    public LoginTest() {
    }

    /**
     * Test of checkUser method, of class Login.
     * 
     * @Result PASS
     */
    @Test
    public void testCheckUser() throws Exception {
        System.out.println("checkUser");
        JFrame frame = null;
        String username = "";
        String password = "";
        String usertype = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkUser(frame, username, password, usertype);
        assertEquals(expResult, result);
        
    }


    /**
     * Test of updateLog method, of class Login.
     */
    @Test
    public void testUpdateLog() {
        System.out.println("updateLog");
        String username = "";
        String type = "";
        Login instance = new Login();
        instance.updateLog(username, type);

    }


    
}
