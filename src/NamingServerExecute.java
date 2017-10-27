import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class NamingServerExecute extends UnicastRemoteObject implements NamingServerInterface
{
    public NamingServerExecute() throws RemoteException
    {
        super();
    }

    public String printNodes() throws RemoteException
    {
        String printAll = "";
        for(Map.Entry<Integer, String> entry : NamingServer.nodeMap.entrySet())

            printAll += "Node: " + entry.getKey() + " - IP address: " + entry.getValue() + "\n";

        return printAll;
    }

    public void removeNode (int hashNode) throws RemoteException
    {
        //key verwijderen
        boolean found = false;

        for( int node : NamingServer.nodeMap.keySet())
        {
            if(node == hashNode)
            {
                found = true;
            }
            else
            {
                found = false;
            }
        }

        if(found)
        {
            NamingServer.nodeMap.remove(hashNode);
            System.out.println("Hash:"+ hashNode +" removed.");
        }
        else
        {
            System.out.println("Node was not found.");
        }

    }

    public void addNode (int hashNode, String address)
    {
        // Add hash node and ip address
        boolean found = false;

        for( int node : NamingServer.nodeMap.keySet())
        {
            if(node == hashNode)
            {
                found = true;
            }
            else
            {
                found = false;
            }
        }

        if(!found)
        {
            NamingServer.nodeMap.put(hashNode, address);
            System.out.println("Node was added.");
        }
        else
        {
            System.out.println("Node already exists.");
        }
    }

    // sauce: https://stackoverflow.com/questions/5009866/how-to-scan-a-folder-for-files-and-their-locations-in-java
    /**public ArrayList<String> scanFiles(String filePath)
     {
     File directory = new File(filePath);
     String [] directoryContents = directory.list();
     ArrayList<String> fileLocations = new ArrayList<String>();
     for(String fileName: directoryContents) { ;
     fileLocations.add(String.valueOf(fileName));
     }
     return fileLocations;
     }**/

    public void addFileList(ArrayList<String> fileList, int hashUser)
    {
        for(String fileName : fileList)
        {
            int fileHash = Math.abs(fileName.hashCode() % 32768);
            NamingServer.fileMap.put(fileHash, hashUser);
        }
    }

    public String searchFile(String fileName)
    {
        int fileHash = Math.abs(fileName.hashCode() % 32768);
        int hashNode = NamingServer.fileMap.get(fileHash);
        return NamingServer.nodeMap.get(hashNode);
    }
}