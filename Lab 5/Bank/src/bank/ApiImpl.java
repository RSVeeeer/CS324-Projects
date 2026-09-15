/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.*;
import java.rmi.server.*;
import api.*;

public class ApiImpl extends UnicastRemoteObject implements Api {
    private static final long serialVersionUID = 1L;
    private Data account = new Data(0);

    public ApiImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized int setBalance(int value) throws RemoteException {
        account.setValue(value);
        System.out.println("new balance: " + account.getValue());
        return account.getValue();
    }    
    
}