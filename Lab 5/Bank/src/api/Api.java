/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

/**
 *
 * @author ultimate7
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Api extends Remote {
    int setBalance(int value) throws RemoteException;
    int addBalance(int amount) throws RemoteException;
    int withdrawBalance(int amount) throws RemoteException;
    double addInterest(double rate) throws RemoteException;

    Data setBalance(Data value) throws RemoteException;
    Data addBalance(Data amount) throws RemoteException;
    Data withdrawBalance(Data amount) throws RemoteException;
    Data addInterest(Data rate) throws RemoteException;
    
}