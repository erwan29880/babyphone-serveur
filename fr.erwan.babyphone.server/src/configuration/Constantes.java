package configuration;

import java.util.ArrayList;

final public class Constantes {

    /** liste des ipv4 autorisées */
    public final static ArrayList<String> authorizedIps = new ArrayList<>();

    /** port d'écoute */
    public final static int PORT = 12345;

    /** le taux d'échantillonage */
    public final static float SAMPLE_RATE = 22050;

    /** le nombre de bits */
    public final static int SAMPLE_SIZE_IN_BITS = 16;

    /** le nombre de canaux audio */
    public final static int CHANNELS = 1;
    

    static {
        authorizedIps.add(
            "/your/ip"
        );
    }
}