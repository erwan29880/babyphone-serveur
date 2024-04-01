package network;
import java.net.InetSocketAddress;
import java.net.Socket;

/** classe pour récupérer l'ip du client qui se connecte */
public class IpExtract {
    
    /** l'ip du client */
    private String ip;

    /** constructeur */
    public IpExtract(Socket clientSocket) {
        this.ip = getIp(clientSocket);
    }

    /** getter de l'ip */
    private String getIp(Socket clientSocket) {
        try {
            String ip = (((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
            return ip;
        } catch (Exception e) {
            return null;
        }
    }

    /** vérification que l'ip est dans les ips autorisées (cf configuration) */
    public boolean isValid() {
        return configuration.Constantes.authorizedIps.contains(ip);
    }
}
