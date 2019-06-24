package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


//TODO: Send Client Name to Server? Not sure how to do that right now, maybe per OutputStream message?

/**
* Client Class, creates the Client and connects it to the Server when it's possible
* */
public class Client {
    private String name;
    private int port;
    private String serverIP;
    private Socket socketClient;

    /**
     * Constructor for Client
     * @param name Client Name
     * @param port Port on which the Client wants to connect
     * @param serverIP Ip of the Server the client wants to connect to
     * */
    Client(String name, int port, String serverIP) {
        this.name=name;
        this.port=port;
        this.serverIP = serverIP;
        connectClient();
    }
    /**
     * Connects the Client to the Server
     *
     * */
    private void connectClient(){
        try{
            //a lot of Print outs  to the console for debugging purpose
            System.out.println("Trying to Connect to: " +serverIP+" on port: " +port);
            socketClient = new Socket(serverIP,port);
            System.out.println("Connected");
        }catch (UnknownHostException ue){
            System.out.println("Unknown Host, Check your ip-address/Port input" +ue.getMessage());
        }
        catch (IOException io){
            System.out.println("IOError: Check your ip-address/Port input " + io.getMessage());
        }
    }
    /**
     * Disconnects the Client from the Server
     *
     * */
    private void closeConnetion(){
        try {
            socketClient.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Getter for the name of the Client
     * @return Name of the Client
     * */
    public String getName(){
        return name;
    }
    /**
     * Getter for the port on which the Client wants to connect
     * @return port of the client
     * */
    public int getPort(){
        return port;
    }
    /**
     * Getter for the serverIp of the Client
     * @return ip of the server the client wants to connect to
     * */
    public String getServerIP(){
        return serverIP;
    }
}
