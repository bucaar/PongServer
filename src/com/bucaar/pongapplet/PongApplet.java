/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bucaar.pongapplet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Aaron
 */
public class PongApplet extends Applet implements Runnable{
    private Graphics graphics;
    private BufferedImage image;
    private int x = 0;
    private boolean p1Win = false;
    private boolean p2Win = false;
    
    protected boolean running = true;
    protected final int scoreToWin = 1;
    protected Ball ball;
    protected Paddle p1;
    protected Paddle p2;
    
    @Override
    public void update(Graphics g){
        if(p1.score == scoreToWin){
            p1Win = true;
            running = false;
        }
        else if(p2.score == scoreToWin){
            p2Win = true;
            running = false;
        }
        p1.update();
        p2.update();
        ball.update();
        paint(g);
    }
    
    @Override
    public void paint(Graphics g){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 680, 480);
        graphics.setFont(new Font("Ariel", Font.BOLD, 50));
        graphics.setColor(Color.GREEN);
        graphics.drawString(p1.score + " | " + p2.score, 680/2-47, 50);
        
        if(p1Win){
            graphics.drawString("Player 1", 70, 230);
            graphics.drawString("WINS", 100, 280);
        }
        if(p2Win){
            graphics.drawString("Player 2", 415, 230);
            graphics.drawString("WINS", 445, 280);
        }
        
        ball.draw(graphics);
        p1.draw(graphics);
        p2.draw(graphics);
        
        g.drawImage(image, 0, 0, null);
    }
    
    public void init(){
        image = new BufferedImage(680, 480, BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();
        ball = new Ball(this);
        p1 = new Paddle(0, this);
        p2 = new Paddle(1, this);
        p1.setOpponent(p2);
        p2.setOpponent(p1);
    }
    
    @Override
    public void run() {
        ServerSocket server = null;
        try{
            server = new ServerSocket(4499);
            Player c1 = new Player(server.accept(), p1, this);
            log("Player 0 connected.");
            Player c2 = new Player(server.accept(), p2, this);
            log("Player 1 connected.");
            c1.start();
            c2.start();
            server.close();
            while(running){
                Thread.sleep(1000/30);
                repaint();
            }
        }
        catch(IOException e){
            log("Could not open ServerSocket on port 4499");
        }
        catch(InterruptedException e){
            log("Main Thread interrupted.");
        }
        finally{
            try{
                if(server!=null){
                    server.close();
                    log("Server closed!");
                }
            }
            catch(IOException e){
                log("Server could not close.");
            }
        }
    }
    
    private void log(String message){
        System.out.println("SERVER: " + message);
    }
}