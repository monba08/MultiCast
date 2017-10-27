import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class Node
{
    public static void main(String[] args) throws NotBoundException, MalformedURLException, UnknownHostException {
        try {
            NamingServerInterface node = (NamingServerInterface) Naming.lookup("rmi://localhost/NamingServerInterface");
            Scanner s = new Scanner(System.in);
            System.out.println("Give name for node:");
            String nodeName = s.nextLine();
            // Search files uitvoering
            // Nodes kunnen verwijderen, en ook vanuit de tweede map
            int nodeHash = Math.abs(nodeName.hashCode() % 32768);

            node.addNode(nodeHash, InetAddress.getLocalHost().getHostAddress());
            int option;
            int exit = 0;

            while (exit != 1)
            {
                // Print menu
                System.out.println("\nMenu:\n\n"
                        + "Option 1 - Show the available nodes      \n"
                        + "Option 2 - Scan local files              \n"
                        + "option 3 - Search node by file           \n"
                        + "option 4 - Exit                          \n");
                option = s.nextInt();

                switch (option) {
                    case 1:
                        System.out.println(node.printNodes());
                        break;
                    case 2:
                        ArrayList<String> fileList = scanFiles();
                        node.addFileList(fileList, nodeHash);
                        break;
                    case 3:
                        System.out.println("Which file do you want to search? (use extensions)");
                        Scanner local = new Scanner(System.in);
                        String fileName = local.nextLine();
                        System.out.println("IP-address for this " +fileName+ " is " + node.searchFile(fileName));
                        // Do you want to ping this IP-address?
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        exit = 1;
                        break;
                    default:
                        System.out.println("Option doesn't exist, please try again.");
                        break;
                }
            }

        } catch (RemoteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // sauce: https://stackoverflow.com/questions/5009866/how-to-scan-a-folder-for-files-and-their-locations-in-java
    public static ArrayList<String> scanFiles()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the filepath.");
        String filePath = s.nextLine();
        File directory = new File(filePath);
        String [] directoryContents = directory.list();
        ArrayList<String> fileLocations = new ArrayList<String>();
        for(String fileName: directoryContents) {
            fileLocations.add(String.valueOf(fileName));
        }
        return fileLocations;
    }










}