/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.awt.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Avadh
 */
public class NodeTest {
    
    public NodeTest() {
    }

    /**
     * Test to see if the node name can be updated
     * 
     * @Result PASS
     */
    @Test
    public void testSetDisplayName() {
        System.out.println("setDisplayName");
        String displayName = "node one";
        Node n1 = new Node();
        n1.setDisplayName(displayName);
        
        assertEquals("node one", n1.getDisplayName());
    }


    /**
     * Test to see if the setColor() method changes the colour of the node
     * 
     * @Result PASS
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        Color color = Color.BLUE;
        Node n1 = new Node();
        n1.setColor(color);
        
        assertEquals(Color.BLUE, n1.getColor());
        
    }


    /**
     * Test to see if the class sets an automatic name for the node 
     * if the user does not specify one
     * 
     * @Result PASS
     */
    @Test
    public void testIsAutomaticName() {
        System.out.println("isAutomaticName");
        Node n1 = new Node();
        boolean expResult = true;
        boolean result = n1.isAutomaticName();
        
        assertEquals(expResult, result);

        
    }


}
