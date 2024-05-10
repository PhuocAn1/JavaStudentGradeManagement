/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class Server {
    private ServerSocket serverSocket;
    private final int PORT = 9090;
    private ExecutorService pool = Executors.newFixedThreadPool(5);
    
    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            serverSocket.close();
           pool.shutdown();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start() {
        try {
            while (true) {
                // Continue to accept connection request from clients
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
                pool.execute(clientHandler);
            }
        } catch (IOException ex) {
            close();
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]) {
        Server server = new Server();
        server.start();
        System.out.println("Closing");
        server.close();
    }
}
