/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.*;
import java.rmi.registry.*;

import api.*;


public class Bank {
     
    /**
     * @param args the command line arguments
     */
    private void startServer(){
        try {
            // create on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // create a new service named myMessage
            registry.rebind(Api.class.getSimpleName(), new ApiImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }      
        System.out.println("system is ready");
    }
    
    public static void main(String[] args) {
        Bank main = new Bank();
        main.startServer();
    }
}