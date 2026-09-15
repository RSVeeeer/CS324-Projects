/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import api.Api;
import api.Data;

public class Customer {
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    private static Registry registry;   
    
    
    
    public static void main(String[] args) throws Exception {
        registry = LocateRegistry.getRegistry(HOST, PORT);
        Api remoteApi = (Api) registry.lookup(Api.class.getSimpleName());
        System.out.println("New balance = " +
            remoteApi.setBalance(new Data(1000)).getValue());
        System.out.println("After deposit = " +
            remoteApi.addBalance(new Data(250)).getValue());
        System.out.println("After withdrawal = " +
            remoteApi.withdrawBalance(new Data(100)).getValue());
        System.out.println("After interest = " +
            remoteApi.addInterest(new Data(10)).getValue());
        
    }
}