/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

/**
 *
 * @author ultimate7
 */

import java.rmi.*;

public interface Api extends Remote {
    public int setBalance(int value)  throws RemoteException;
}
