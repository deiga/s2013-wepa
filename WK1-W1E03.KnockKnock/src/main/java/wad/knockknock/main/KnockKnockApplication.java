package wad.knockknock.main;

import wad.knockknock.client.KnockKnockClient;
import wad.knockknock.server.KnockKnockServer;

public class KnockKnockApplication {
    public static void main(String[] args) throws Exception {
        int port = 12345;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        // Käynnistetään palvelin taustalle
        KnockKnockServer server = new KnockKnockServer(port);
        server.start();

        // Odotetaan palvelimen käynnistymistä hetki
        Thread.sleep(500);

        // Käynnistetään asiakasohjelma
        KnockKnockClient client = new KnockKnockClient(port);
        client.start();
    }
}
