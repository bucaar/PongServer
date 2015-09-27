/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongserver;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Aaron
 */
public class PongServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame f = new JFrame("Pong Visualizer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(700, 550));
        com.bucaar.pongapplet.PongApplet a = new com.bucaar.pongapplet.PongApplet();
        a.init();
        new Thread(a).start();
        f.add(a);
        f.pack();
        f.setVisible(true);
    }
    
}
