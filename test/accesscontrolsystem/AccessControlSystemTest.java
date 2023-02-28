/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Avadh
 */
public class AccessControlSystemTest {
    
    public AccessControlSystemTest() {
    }


    /**
     * Test of getLog method, of class AccessControlSystem.
     * 
     * @Result PASS
     */
    @Test
    public void testGetLog() {
        System.out.println("getLog");
        String query = "";
        AccessControlSystem instance = new AccessControlSystem();
        ResultSet expResult = null;
        ResultSet result = instance.getLog(query);
        assertEquals(expResult, result);

    }

    /**
     * Test to see if the main method runs the program by opening
     * the login page
     * 
     * @Result PASS
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        AccessControlSystem.main(args);

    }
    
}
