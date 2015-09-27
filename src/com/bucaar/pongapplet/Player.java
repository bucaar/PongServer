/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bucaar.pongapplet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Aaron
 */
public class Player extends Thread{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Paddle p;
    private PongApplet a;
    
    public Player(Socket socket, Paddle p, PongApplet a){
        this.socket = socket;
        this.p = p;
        this.a = a;
    }
    
    @Override
    public void run(){
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            while(true){
                String input = in.readLine();
                if(input == null || input.equals("")){
                    break;
                }
                else if(input.startsWith("MOVE")){
                    if(input.length()<5){
                        out.println("ERROR");
                        return;
                    }
                    input = input.substring(5);
                    if(input.startsWith("up")){
                        if(input.length()<3)
                            p.setUp(1);
                        else{
                            try{
                                p.setUp(Double.parseDouble(input.substring(3)));
                            }
                            catch(NumberFormatException e){
                                out.println("ERROR");
                                continue;
                            }
                        }
                        out.println("OK");
                    }
                    else if(input.startsWith("down")){
                        if(input.length()<5)
                            p.setDown(1);
                        else{
                            try{
                                p.setDown(Double.parseDouble(input.substring(5)));
                            }
                            catch(NumberFormatException e){
                                out.println("ERROR");
                                continue;
                            }
                        }
                        out.println("OK");
                    }
                    else if(input.equals("stop")){
                        p.setStop();
                        out.println("OK");
                    }
                    else{
                        out.println("ERROR");
                    }
                }
                else if(input.startsWith("GET")){
                    if(input.length()<4){
                        out.println("ERROR");
                        return;
                    }
                    input = input.substring(4);
                    String[] requests = input.split(",");
                    if(requests.length == 0){
                        out.println("ERROR");
                        return;
                    }
                    StringBuilder output = new StringBuilder();
                    boolean follows = false;
                    for(String s : requests){
                        if(s.equals("screenSize")){
                            if(follows){
                                output.append(",");
                            }
                            output.append("680,480");
                            follows = true;
                        }
                        else if(s.equals("ballLocation")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(a.ball.x).append(",").append(a.ball.y);
                            follows = true;
                        }
                        else if(s.equals("ballSize")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(a.ball.d);
                            follows = true;
                        }
                        else if(s.equals("ballVelocity")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(a.ball.dx).append(",").append(a.ball.dy);
                            follows = true;
                        }
                        else if(s.equals("myPaddle")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(p.x).append(",").append(p.y).append(",").append(p.dy);
                            follows = true;
                        }
                        else if(s.equals("opponentPaddle")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(p.opponent.x).append(",").append(p.opponent.y).append(",").append(p.opponent.dy);
                            follows = true;
                        }
                        else if(s.equals("paddleSize")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(p.w).append(",").append(p.h);
                            follows = true;
                        }
                        else if(s.equals("score")){
                            if(follows){
                                output.append(",");
                            }
                            output.append(p.score).append(",").append(p.opponent.score);
                            follows = true;
                        }
                        else{
                            out.println("ERROR");
                            return;
                        }
                    }
                    out.println(output.toString());
                }
            }
        }
        catch(IOException e){
            log("Could not read line from client..");
        }
        finally{
            try{
                if(socket!=null){
                    log("Socket closing...");
                    socket.close();
                    log("Socket is closed!");
                }
            }
            catch(IOException e){
                log("Socket could not close.");
            }
            
        }
    }
    
    private void log(String message){
        System.out.println("PLAYER " + p.id + ": " + message);
    }
}
