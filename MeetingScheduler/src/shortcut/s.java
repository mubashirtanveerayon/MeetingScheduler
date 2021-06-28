/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortcut;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mslinks.ShellLink;

/**
 *
 * @author ayon2
 */
public class s {
    public static void main(String[] args) {
        new s();
    }
    s(){
        ShellLink sl = new ShellLink();
        try {
            ShellLink.createLink("C:\\Users\\ayon2\\Desktop\\MS\\Meeting Scheduler\\Meeting Scheduler.exe", "C:\\Users\\ayon2\\Desktop\\MS.lnk");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
