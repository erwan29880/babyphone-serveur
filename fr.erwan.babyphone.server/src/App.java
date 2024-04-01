import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.IpExtract;
import streaming.ClientHandler;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/** classe principale du serveur */
public class App {

    private static final Logger LOGGER = Logger.getLogger("log");

    /** configuration du logging */
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs.xml");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException ex) {
            LOGGER.warning("Could not load configuration file. Using default configuration.");
        }
    }
   
    public static void main(String[] args) {
        
        try (ServerSocket serverSocket = new ServerSocket(configuration.Constantes.PORT)) {
            LOGGER.info("Server listening on port " + configuration.Constantes.PORT);
            
            // boucle pour pouvor ré-accepter un client
            while (true) {
                Socket clientSocket = serverSocket.accept();
                IpExtract ip = new IpExtract(clientSocket);
                
                // vérification ip
                if (ip.isValid()) {
                    LOGGER.info("New client connected: " + clientSocket.toString());
                    Thread clientHandler = new Thread(new ClientHandler(clientSocket));
                    clientHandler.start();
                } else {
                    LOGGER.warning("client rejeté: " + clientSocket.toString());
                    clientSocket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
