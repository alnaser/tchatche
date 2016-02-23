/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author alnaser
 */
public class ThreadComm extends Thread {

    Socket client1;
    Socket client2;

    public ThreadComm(Socket c1, Socket c2) {
        client1 = c1;
        client2 = c2;
    }

    @Override
    public void run() {
        System.out.println("im in new ThreadComm");
        while (true) {
            
            String msg = read(client1);
            if(">>end".equals(msg) || msg==null ){
                System.out.println("breaking threadComm");
                break;
            }
            System.out.println("MSG received:"+msg);
            if(msg.startsWith(">>"))
                write(client2, msg);
        }
        System.out.println("Thread comm stopped");
    }

    String read(Socket client) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            return bf.readLine();

        } catch (IOException ex) {
            System.out.println("erreur read");
            return null;
        }
    }

    void write(Socket client, String msg) {
        try {

            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            pw.println(msg);

        } catch (IOException ex) {
            System.out.println("erreur write");
        }
    }

}
