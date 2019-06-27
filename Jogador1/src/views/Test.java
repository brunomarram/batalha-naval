/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package views;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class implements java socket client
 * @author pankaj
 *
 */
public class Test {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        for(int i=0; i<5;i++){
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream                    
            oos = Test.write(socket, ""+i, oos);
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            Test.read(socket, ois);
            //close resources
            ois.close();
            oos.close();
        }
    }
    
    public static ObjectOutputStream write(Socket socket, String message, ObjectOutputStream oos) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending request to Socket Server");
        oos.writeObject(message);
        return oos;
    }
    
    public static String read(Socket socket, ObjectInputStream ois) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        String message = (String) ois.readObject();
        System.out.println("Message: " + message);
        return message;
    }
}
