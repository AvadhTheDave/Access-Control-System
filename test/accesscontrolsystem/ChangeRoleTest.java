/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Avadh
 */
public class ChangeRoleTest {
    
    public ChangeRoleTest() {
    }

    /**
     * Test of updateRole method, of class ChangeRole.
     */
    @Test
    public void testUpdateRole() {
        System.out.println("updateRole");
        String user = "";
        String role = "";
        ChangeRole instance = new ChangeRole();
        instance.updateRole(user, role);

    }
    
}
