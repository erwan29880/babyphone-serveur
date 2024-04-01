package streaming;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * classe pour le streaming audio du micro du serveur vers le client
 *  sous classe dans un autre Thread pour recevoir des messages du client
 */
public class ClientHandler implements Runnable {

    /** logger */
    private final Logger LOGGER = Logger.getLogger("log");

    /** variable pour éteindre le serveur à partir du front */
    private static boolean isClosed = false;

    /** le client connecté */
    private Socket clientSocket;

    /** la ligne d'entrée audio */
    private TargetDataLine line;

    /** la sortie vers le client */
    private OutputStream outputStream;

    /** constructeur
     * @param clientSocket le client connecté
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /** 
     * Streaming audio vers le client connecté
     * implémentation de l'interface callable 
     * */
    @Override
    public void run() {
        try {
            // Audio setup et capture
            AudioFormat audioFormat = getAudioFormat();
            line = AudioSystem.getTargetDataLine(audioFormat);
            line.open(audioFormat);
            line.start();

            // configuration de la sortie vers le client
            outputStream = clientSocket.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            // thread pour recevoir un message du client
            new MessageHandler().start();

            // boucle de streaming
            LOGGER.info("start streaming");
            while (!isClosed || !clientSocket.isClosed()) {
                bytesRead = line.read(buffer, 0, buffer.length);
                if (bytesRead == -1) {
                    break;
                }
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (SocketException e) {
            LOGGER.info("Client disconnected: " + clientSocket);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (line != null) {
                    line.stop();
                    line.close();
                }
                if (outputStream != null) outputStream.close();
                clientSocket.close();
                LOGGER.info("Client disconnected: " + clientSocket);
                if (isClosed) System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
                if (isClosed) System.exit(0);
            }
        }
    }

    /** la configuration audio */
    private AudioFormat getAudioFormat() {
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(
            configuration.Constantes.SAMPLE_RATE, 
            configuration.Constantes.SAMPLE_SIZE_IN_BITS, 
            configuration.Constantes.CHANNELS, 
            signed, 
            bigEndian);
    }

    /** écoute d'un éventuel message du client, pour stopper le serveur */
    private class MessageHandler extends Thread {
        @Override 
        public void run() {
            try (Scanner scanner = new Scanner(clientSocket.getInputStream())) {
                LOGGER.info("input listening");
                while (!isClosed || scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    LOGGER.info("Received message from client: " + message);
                    if (message.equalsIgnoreCase("stop".trim())) {
                        LOGGER.info("received stop from client, serveur will shut down");
                        isClosed = true;
                    }
                }
                if (scanner != null) scanner.close();
            } catch (IOException e) {
                LOGGER.info("Impossible de lire le flux d'entrée");
                return;
            }
        }
    }
}
