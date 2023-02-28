/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.io.File;
import java.nio.file.Path;
import javax.swing.JFrame;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Avadh
 */
public class FileManagerTest {
    
    public FileManagerTest() {
    }

    /**
     * Test that the method can take parameters and open files
     * 
     * @Result PASS
     */
    @Test
    public void testOpenFile() throws Exception {
        System.out.println("openFile");
        JFrame frame = null;
        String dir = "";
        String key = "";
        String file = "";
        FileManager instance = new FileManager();
        instance.openFile(frame, dir, key, file);

    }

    /**
     * Test to see if the method can take parameters and remove a file
     * 
     * @Result PASS
     */
    @Test
    public void testRemoveFile() {
        System.out.println("removeFile");
        String fileName = "";
        FileManager instance = new FileManager();
        instance.removeFile(fileName);

    }


    /**
     * Test of updateFileRole method, of class FileManager.
     * 
     * @Result PASS
     */
    @Test
    public void testUpdateFileRole() {
        System.out.println("updateFileRole");
        JFrame frame = null;
        String file = "";
        String role = "";
        FileManager instance = new FileManager();
        instance.updateFileRole(frame, file, role);

    }


}
