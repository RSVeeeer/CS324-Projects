/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.rmi.registry.*;
import api.*;

public class Customer {
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    private static Registry registry;   
    
    
    
    public static void main(String[] args) throws Exception {
        registry = LocateRegistry.getRegistry(HOST, PORT);
        Api remoteApi = (Api) registry.lookup(Api.class.getSimpleName());
        
        System.out.println("New balance = " +
                remoteApi.setBalance(1000));
        
    }
}