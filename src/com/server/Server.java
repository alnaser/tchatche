/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author alnaser
 */
public class Server {

    static Vector<Socket> vs = new Vector();
    static Vector<String> vlogins = new Vector();

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            ServerSocket ss = new ServerSocket(9090);
            while (true) {
                System.out.println("!");
                Socket s = ss.accept();
                System.out.println("accept");
                vs.add(s);
                new ServerThread(s).start();

            }
        } catch (IOException e) {
            System.out.println("erreur io");
        }
    }

}
