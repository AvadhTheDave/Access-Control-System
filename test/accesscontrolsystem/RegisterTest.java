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
public class RegisterTest {
    
    public RegisterTest() {
    }

    /**
     * Test of addUser method, of class Register.
     * 
     * @Result PASS
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        JFrame frame = null;
        String username = "";
        String password = "";
        String usertype = "";
        String role = "";
        Register instance = new Register();
        instance.addUser(frame, username, password, usertype, role);

    }


    
}
