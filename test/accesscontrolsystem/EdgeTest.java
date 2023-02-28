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
public class EdgeTest {
    
    Node n1;
    Node n2;
    
    public EdgeTest() {
        n1 = new Node();
        n2 = new Node();
    }

    /**
     * Test of getColor method, of class Edge.
     * 
     * @Result PASS
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Edge e1 = new Edge(n1, n2);
        e1.setColor(Color.BLUE);
        Color expResult = Color.BLUE;
        Color result = e1.getColor();
        assertEquals(expResult, result);

        
    }

    /**
     * Test of getWeight method, of class Edge.
     * 
     * @Result PASS
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        Edge e1 = new Edge(n1, n2);
        e1.setWeight(5);
        int expResult = 5;
        int result = e1.getWeight();
        assertEquals(expResult, result);

    }




    /**
     * Test of isWeighted method, of class Edge.
     * 
     * @Result PASS
     */
    @Test
    public void testIsWeighted() {
        System.out.println("isWeighted");
        Edge e1 = new Edge(n1, n2);
        boolean expResult = false;
        boolean result = e1.isWeighted();
        assertEquals(expResult, result);

    }




}
