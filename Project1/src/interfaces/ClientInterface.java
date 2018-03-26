/**
 * Trying to use RMI
 * */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
    public String hello(String name) throws RemoteException;
}