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
public class Ball{
    protected double x, y, d, dx, dy, t, s;
    protected PongApplet a;
    protected Color c;
            
    public Ball(PongApplet a){
        this.a = a;
        x = 680/2-5;
        y = 480/2-5;
        t = 5.495;
        s = 20;
        dx = s * Math.cos(t);
        dy = -s * Math.sin(t);
        d = 10;
        c = Color.GREEN;
    }
    
    public void draw(Graphics g) {
        g.setColor(c);
        g.fillOval((int)x, (int)y, (int)d, (int)d);
    }

    public void update() {
        dx = s * Math.cos(t);
        dy = -s * Math.sin(t);
        
        int intervals = (int)(s/10);
        
        for(int i=0;i<intervals;i++){
            x += dx/intervals;
            y += dy/intervals;
        
            if(y<0){
                y += 2*(0-y);
                t=6.28-t;
                dy*=-1;
            }
            else if(y>480-d){
                y-= 2*(y-(480-d));
                t=6.28-t;
                dy*=-1;
            }
            
            if(x<-10){
                a.p2.addScore();
                reset(1);
                dx*=-1;
            }
            if(x>680){
                a.p1.addScore();
                reset(0);
                dx*=-1;
            }
            
            if(x<=50+10 && dx<0 && x>=50-10-10 && y+10>=a.p1.y && y<=a.p1.y + 100){
                x+=2*((50+10)-x);
                double py = y - a.p1.y;
                if(dy>0)
                    if(py<-5)
                        t=6.28-t;
                    else
                        t=9.42-t;
                else if(dy<0)
                    if(py>95)
                        t=6.28-t;
                    else
                        t = 3.14-t;
                if(py<20){
                    t+=.17;
                }
                else if(py>=20&&py<40){
                    t+=.09;
                }
                else if(py>=60&&py<80){
                    t-=.09;
                }
                else if(py>80){
                    t-=.17;
                }
                s+=.3;
                dx*=-1;
            }
            else if(x+10>=(680-50-10) && dx>0 && x<=680-50-10+10 && y+10>=a.p2.y&&y<=a.p2.y+100){
                x+=2*((680-50-10)-(x+10));
                double py = y - a.p2.y;
                if(dy<0)
                    if(dx<-5)
                        t=6.28-t;
                    else
                        t=3.14-t;
                else if(dy>0)
                    if(dx>105)
                        t=6.28-t;
                    else
                        t=9.42-t;
                if(py<20){
                    t-=.17;
                }
                else if(py>=20&&py<40){
                    t-= .09;
                }
                else if(py>=60&&py<80){
                    t+=.09;
                }
                else if(py>80){
                    t+=.17;
                }
                s+=.3;
                dx*=-1;
            }
        }
    }
    
    public void reset(int i){
        x = 680/2 - 5;
        y = 480/2 - 5;
        s = 20;
        if(i==1){
            t = 5.495;
        }
        if(i==0){
            t = 3.925;
        }
    }
}
