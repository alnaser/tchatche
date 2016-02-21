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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alnaser
 */
public class ServerThread extends Thread {

    Socket client;

    public ServerThread(Socket client) {
        this.client = client;

    }

    public String listClient() {
        String tmp = "";
        for (String s : Server.vlogins) {
            tmp += s + ":";
        }

        return tmp;

    }

    @Override
    public void run() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            System.out.println("in run");

            String tmp = bf.readLine();
            //System.out.println(tmp);
            Server.vlogins.add(tmp);
            Thread comm = null;

            while (true) {
                System.out.println("im in serverthread while");

//                BufferedReader bf = new BufferedReader(new InputStreamReader(clients.getInputStream()));
                String choix="";
//                if(comm==null || !comm.isAlive())
                    choix = bf.readLine();

                System.out.println("choix: " + choix);
                if (choix != null) {
                    switch (choix) {
                        case "liste":
                            //System.out.println("envoi a client");
                            String liste = this.listClient();

                            pw.println(liste);
                            break;
                        case "connect":
                            String login = bf.readLine();
                            System.out.println(login != null ? login : "null");
                            if (Server.vlogins.contains(login)) {
                                System.out.println("contain");
                                int indexOf = Server.vlogins.indexOf(login);
                                Socket client2 = Server.vs.get(indexOf);
                                comm = new Thread(new ThreadComm( client,client2));
                                comm.start();
                                comm.join();

                            }

                    }
                } else {
                    System.out.println("Thread stopped");
                    if (comm != null) {
                        comm.stop();
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Erreur en serveurThread");
        } catch (InterruptedException ex) {
            System.out.println("join probleme");
        }

    }

}
