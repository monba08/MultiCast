import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface NamingServerInterface extends Remote
{
    public String printNodes() throws RemoteException;
    public void removeNode (int hashNode) throws RemoteException;
    public void addNode (int hashNode, String address) throws RemoteException;
    //public ArrayList<String> scanFiles(String filePath) throws RemoteException;
    public void addFileList(ArrayList<String> fileList, int hashUser) throws RemoteException;
    public String searchFile(String fileName) throws RemoteException;
}
