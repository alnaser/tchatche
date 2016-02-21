/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JComboBox;

/**
 * @author alnaser
 */
public class JComboSynchron extends JComboBox implements Runnable {

    Socket s = null;
    String login = "";

    public JComboSynchron(Socket s, String login) {
        this.s = s;
        this.login = login;
    }

    @Override
    public void run() {
        int length = 0;
        while (true) {
            write(s, "liste");
//            write(s, "liste");
            String[] split = read(s).split(":");
            if (split.length > length) {
                length = split.length;
                removeAllItems();
                for (String str : split) {
                    if (!str.equals(login)) {
                        addItem(str);
                    }
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                System.out.println("Erreur sleep");
            }
        }
    }

    String read(Socket client) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            return bf.readLine();

        } catch (IOException ex) {
            System.out.println("erreur read");
            return "null";
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
