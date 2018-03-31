package interfaces;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
    public String hello(String name) throws RemoteException;
    public void backup(String filePath, String replicationDegree) throws IOException;
    public void restore(String fileName) throws IOException;
    public void delete(String fileName) throws IOException;
}