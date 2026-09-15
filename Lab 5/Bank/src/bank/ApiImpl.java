/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import api.Api;
import api.Data;

public class ApiImpl extends UnicastRemoteObject implements Api {
    private static final long serialVersionUID = 1L;
    private Data account = new Data(0);

    public ApiImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized int addBalance(int amount) throws RemoteException {
        return setBalance(account.getValue() + amount);
    }

    @Override
    public synchronized int withdrawBalance(int amount) throws RemoteException {
        return setBalance(account.getValue() - amount);
    }

    @Override
    public synchronized double addInterest(double rate) throws RemoteException {
        double newBalance = account.getValue() * (1 + rate / 100);
        account.setValue((int) newBalance);
        System.out.println("new balance: " + account.getValue());
        return newBalance;
    }

    @Override
    public synchronized Data setBalance(Data value) throws RemoteException {
        setBalance(value.getValue());
        return new Data(account.getValue());
    }

    @Override
    public synchronized Data addBalance(Data amount) throws RemoteException {
        addBalance(amount.getValue());
        return new Data(account.getValue());
    }

    @Override
    public synchronized Data withdrawBalance(Data amount) throws RemoteException {
        withdrawBalance(amount.getValue());
        return new Data(account.getValue());
    }

    @Override
    public synchronized Data addInterest(Data rate) throws RemoteException {
        addInterest(rate.getValue());
        return new Data(account.getValue());
    }

    @Override
    public synchronized int setBalance(int value) throws RemoteException {
        account.setValue(value);
        System.out.println("new balance: " + account.getValue());
        return account.getValue();
    }    
    
}