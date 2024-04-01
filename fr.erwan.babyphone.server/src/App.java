import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.IpExtract;
import streaming.ClientHandler;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Serveur de streaming audio : 
 *  le serveur récupére l'audio du micro 
 *  le serveur attend qu'un client se connecte
 *  le serveur diffuse l'audio au client
 *  le client peut éteindre le serveur
 * 
 * Classe principale 
 */
public class App {

    /** logger */
    private static final Logger LOGGER = Logger.getLogger("log");

    /** configuration du logging */
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs.xml");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException ex) {
            LOGGER.warning("Impossible de charger le fichier de logs, utilisation par défaut");
        }
    }
   
    public static void main(String[] args) {
        
        // initialisation du socket serveur
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
