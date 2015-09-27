/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bucaar.pongapplet;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Aaron
 */
public class Paddle{
    protected double x, y, w, h, dy;
    protected int score, id;
    protected PongApplet a;
    protected Paddle opponent;
    protected Color c;
    
    public Paddle(int i, PongApplet a){
        if(i==0){
            x = 50;
            id = i;
        }
        if(i==1){
            x = 680-50-10;
            id = i;
        }
        y = 480/2-50;
        dy = 0;
        w = 10;
        h = 100;
        c = Color.GREEN;
        score = 0;
        this.a = a;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect((int)x, (int)y, (int)w, (int)h);
    }

    public void update() {
        y += dy;
        if(y<0)y=0;
        if(y>480-100)y=480-100;
    }
    
    public String toString(){
        return "" + id;
    }
    
    public void addScore(){
        score++;
    }
    
    public void setUp(double percentage){
        if(percentage<0)percentage = 0;
        if(percentage>1)percentage = 1;
        dy = -8*percentage;
    }
    
    public void setDown(double percentage){
        if(percentage<0)percentage = 0;
        if(percentage>1)percentage = 1;
        dy = 8*percentage;
    }
    
    public void setStop(){
        dy = 0;
    }
    
    public void setOpponent(Paddle o){
        opponent = o;
    }
}

