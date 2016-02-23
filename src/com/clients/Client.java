/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clients;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author alnaser
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws InterruptedException {
        try {
            Socket s = new Socket("localhost", 9090);
            Thread.sleep(3000);
            GUI g=new GUI(s, "alnaser 1");
            new Thread(g).start();
        } catch (IOException ex) {
            System.out.println("probleme de socket client ");
        }
    }

}
